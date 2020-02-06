package com.okurchenko.weatherdemoapplication.ui.main.fragments.main

import androidx.lifecycle.*
import com.okurchenko.weatherdemoapplication.interactors.*
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentAirQuality
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData
import com.okurchenko.weatherdemoapplication.repositories.db.model.HeaderData
import com.okurchenko.weatherdemoapplication.repositories.db.model.WeatherForecast
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder
import com.okurchenko.weatherdemoapplication.utils.error.AutoRemoveCompleteSourceMediatorLiveData
import com.okurchenko.weatherdemoapplication.utils.error.GeneralErrorManager
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val getDeviceLocation: GetDeviceLocation,
    private val getHeaderImage: GetHeaderImage,
    private val getHeaderData: GetHeaderData,
    private val getAirQuality: GetAirQuality,
    private val getTransformedAirQuality: GetTransformedAirQuality,
    private val getWeather: GetWeather,
    private val getWeatherForecast: GetWeatherForecast
) : ViewModel() {

    val updateAirAction =
        { airQualityData.value?.let { currentAirQuality.addCustomSource(fetchCurrentAirQuality(it)) } }
    val updateAirQualityForecast =
        { airQualityData.value?.let { airQualityForecast.addCustomSource(fetchAirQualityForecast(it)) } }
    val updateCurrentWeatherData =
        { deviceLocation.value?.let { currentWeatherData.addCustomSource(fetchCurrentWeatherData(it)) } }
    val updateWeatherForecast =
        { deviceLocation.value?.let { weatherForecast.addCustomSource(fetchWeatherForecast(it)) } }
    private val deviceLocation = MutableLiveData<Pair<Double, Double>>()
    private val headerUrl = AutoRemoveCompleteSourceMediatorLiveData<String>()
    private val headerContentData = AutoRemoveCompleteSourceMediatorLiveData<HeaderData>()
    private val airQualityForecast = AutoRemoveCompleteSourceMediatorLiveData<List<CurrentAirQuality>>()
    private val currentWeatherData = AutoRemoveCompleteSourceMediatorLiveData<CurrentWeatherData>()
    private val weatherForecast = AutoRemoveCompleteSourceMediatorLiveData<List<WeatherForecast>>()
    private val currentAirQuality = AutoRemoveCompleteSourceMediatorLiveData<CurrentAirQuality>()
    private val airQualityData: LiveData<ResourceHolder<List<CurrentAirQuality>>> =
        Transformations.switchMap(deviceLocation) { locationPair ->
            getAirQuality.execute(GetAirQuality.Params(locationPair.first, locationPair.second, errorManager))
        }
    private val errorManager = GeneralErrorManager()

    fun getHeaderData(): LiveData<ResourceHolder<HeaderData>> = headerContentData

    fun getHeaderImageUrl(): LiveData<ResourceHolder<String>> = headerUrl

    fun getCurrentAirQuality(): LiveData<ResourceHolder<CurrentAirQuality>> = currentAirQuality

    fun getAirQualityForecast(): LiveData<ResourceHolder<List<CurrentAirQuality>>> = airQualityForecast

    fun getCurrentWeatherData(): LiveData<ResourceHolder<CurrentWeatherData>> = currentWeatherData

    fun getWeatherForecast(): LiveData<ResourceHolder<List<WeatherForecast>>> = weatherForecast

    fun getDeviceLocation(): MutableLiveData<Pair<Double, Double>> = deviceLocation

    fun getAirQualityData(): LiveData<ResourceHolder<List<CurrentAirQuality>>> = airQualityData

    fun fetchLocation() = viewModelScope.launch {
        val location = getDeviceLocation.execute()
        val coordinates = Pair(location.latitude, location.longitude)
        if (deviceLocation.value?.first != coordinates.first && deviceLocation.value?.second != coordinates.second) {
            deviceLocation.value = coordinates
        }
    }

    fun forceUpdateAll() {
        deviceLocation.value?.let { deviceLocation.value = Pair(it.first, it.second) }
    }

    fun fetchHeaderContent(width: Int, height: Int) {
        fetchHeaderImage(width, height)
        fetchHeaderContentData()
    }

    private fun fetchHeaderImage(width: Int, height: Int) {
        val headerImageParams = GetHeaderImage.Params(currentWeatherData.value, width, height)
        val imageUrl = getHeaderImage.execute(headerImageParams)
        headerUrl.addCustomSource(imageUrl)
    }

    private fun fetchHeaderContentData() {
        val headerData = getHeaderData.execute(GetHeaderData.Params(currentWeatherData.value))
        headerContentData.addCustomSource(headerData)
    }

    private fun fetchCurrentAirQuality(
        airQualities: ResourceHolder<List<CurrentAirQuality>>
    ): MutableLiveData<ResourceHolder<CurrentAirQuality>> {
        val params = GetTransformedAirQuality.Params(airQualities, GetTransformedAirQuality.Action.GET_FIRST)
        return getTransformedAirQuality.execute(params) as MutableLiveData<ResourceHolder<CurrentAirQuality>>
    }

    private fun fetchAirQualityForecast(
        airQualities: ResourceHolder<List<CurrentAirQuality>>
    ): LiveData<ResourceHolder<List<CurrentAirQuality>>> {
        val params = GetTransformedAirQuality.Params(airQualities, GetTransformedAirQuality.Action.GET_EXCEPT_FIRST)
        return getTransformedAirQuality.execute(params) as LiveData<ResourceHolder<List<CurrentAirQuality>>>
    }

    private fun fetchCurrentWeatherData(
        locationPair: Pair<Double, Double>
    ): LiveData<ResourceHolder<CurrentWeatherData>> {
        return getWeather.execute(GetWeather.Params(locationPair.first, locationPair.second, errorManager))
    }

    private fun fetchWeatherForecast(
        locationPair: Pair<Double, Double>
    ): LiveData<ResourceHolder<List<WeatherForecast>>> {
        return getWeatherForecast.execute(
            GetWeatherForecast.Params(locationPair.first, locationPair.second, errorManager)
        )
    }

}