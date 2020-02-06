package com.okurchenko.weatherdemoapplication.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import com.okurchenko.weatherdemoapplication.BuildConfig
import com.okurchenko.weatherdemoapplication.network.WeatherApi
import com.okurchenko.weatherdemoapplication.network.model.*
import com.okurchenko.weatherdemoapplication.repositories.base.BaseNetworkRepository
import com.okurchenko.weatherdemoapplication.repositories.base.NetworkBoundResource
import com.okurchenko.weatherdemoapplication.repositories.base.NetworkResult
import com.okurchenko.weatherdemoapplication.repositories.db.WeatherDatabase
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentAirQuality
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData
import com.okurchenko.weatherdemoapplication.repositories.db.model.WeatherForecast
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder
import com.okurchenko.weatherdemoapplication.utils.addSleepIfDebug
import com.okurchenko.weatherdemoapplication.utils.diffTimeInMins
import com.okurchenko.weatherdemoapplication.utils.getNowTime
import javax.inject.Inject
import javax.inject.Singleton

const val currentWeatherSyncTime = "CURRENT_WEATHER_SYNC_TIME_KEY"
const val weatherForecastSyncTime = "WEATHER_FORECAST_SYNC_TIME"
const val airSyncTime = "AIR_SYNC_TIME"

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDb: WeatherDatabase,
    private val preferences: SharedPreferences
) : BaseNetworkRepository(), WeatherRepository {

    override fun fetchAirQuality(
        lat: Double,
        lon: Double
    ): LiveData<ResourceHolder<List<CurrentAirQuality>>> {
        return object : NetworkBoundResource<List<CurrentAirQuality>, QualityResponse>() {
            override fun fetchData(): List<CurrentAirQuality> {
                addSleepIfDebug()
                return weatherDb.currentAirQualityDao().getAll()
            }

            override fun isStorageDataOld(storageData: List<CurrentAirQuality>?): Boolean =
                isMoreThanMin(airSyncTime)

            override suspend fun networkCall(): NetworkResult<QualityResponse> =
                safeApiCall { weatherApi.airQualityAsync(lat, lon, BuildConfig.API_KEY).await() }

            override fun mapResponse(response: QualityResponse?): List<CurrentAirQuality> {
                val airQualityData = mutableListOf<CurrentAirQuality>()
                response?.data?.forEach { qualityResponse ->
                    val mappedData = qualityResponse.toCurrentAirQuality()
                    mappedData?.run { airQualityData.add(mappedData) }
                }
                return airQualityData
            }

            override fun saveResult(result: List<CurrentAirQuality>) {
                weatherDb.currentAirQualityDao().insertAirQuality(result)
                preferences.edit { putLong(airSyncTime, getNowTime()) }
            }
        }.resultAsLiveData()
    }

    override fun fetchWeather(lat: Double, lon: Double) =
        object : NetworkBoundResource<CurrentWeatherData, CurrentWeatherResponse>() {
            override fun fetchData(): CurrentWeatherData? {
                addSleepIfDebug()
                return weatherDb.currentWeatherDataDao().getAll()
            }

            override fun isStorageDataOld(storageData: CurrentWeatherData?): Boolean =
                isMoreThanMin(currentWeatherSyncTime)

            override suspend fun networkCall(): NetworkResult<CurrentWeatherResponse> =
                safeApiCall { weatherApi.currentWeatherByLocationAsync(lat, lon, BuildConfig.API_KEY).await() }

            override fun mapResponse(response: CurrentWeatherResponse?): CurrentWeatherData? =
                response?.toCurrentWeatherData()

            override fun saveResult(result: CurrentWeatherData) {
                weatherDb.currentWeatherDataDao().insertWeather(result)
                preferences.edit { putLong(currentWeatherSyncTime, getNowTime()) }
            }

        }.resultAsLiveData()

    override fun fetchDailyWeatherForecast(lat: Double, lon: Double): LiveData<ResourceHolder<List<WeatherForecast>>> =
        object : NetworkBoundResource<List<WeatherForecast>, WeatherForecastResponse>() {
            override fun fetchData(): List<WeatherForecast>? {
                addSleepIfDebug()
                return weatherDb.weatherForecastDao().getAll()
            }

            override fun isStorageDataOld(storageData: List<WeatherForecast>?): Boolean =
                isMoreThanMin(weatherForecastSyncTime)

            override suspend fun networkCall(): NetworkResult<WeatherForecastResponse> =
                safeApiCall { weatherApi.weatherForecastAsync(lat, lon, BuildConfig.API_KEY).await() }

            override fun mapResponse(response: WeatherForecastResponse?): List<WeatherForecast>? {
                val forecastData = mutableListOf<WeatherForecast>()
                response?.data?.forEach { dailyData ->
                    val mappedData = dailyData.toWeatherForecast()
                    mappedData?.run { forecastData.add(mappedData) }
                }
                return forecastData
            }

            override fun saveResult(result: List<WeatherForecast>) {
                weatherDb.weatherForecastDao().insertWeatherForecast(result)
                preferences.edit { putLong(weatherForecastSyncTime, getNowTime()) }
            }

        }.resultAsLiveData()

    private fun isMoreThanMin(key: String): Boolean {
        return preferences.getLong(key, 0).diffTimeInMins() >= 10
    }
}


