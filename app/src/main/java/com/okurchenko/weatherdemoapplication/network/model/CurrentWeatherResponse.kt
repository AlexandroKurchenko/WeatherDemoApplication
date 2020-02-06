package com.okurchenko.weatherdemoapplication.network.model

import com.google.gson.annotations.SerializedName
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData
import com.okurchenko.weatherdemoapplication.utils.addCelsiusToTemp
import com.okurchenko.weatherdemoapplication.utils.addPercent

data class CurrentWeatherResponse(
    @SerializedName("data") val data: List<DataEntity>,
    @SerializedName("count") val count: Int
)

data class DataEntity(
    @SerializedName("rh") val rh: Double,
    @SerializedName("pod") val pod: String,
    @SerializedName("lon") val lon: Double,
    @SerializedName("pres") val pres: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("ob_time") val ob_time: String,
    @SerializedName("country_code") val country_code: String,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("ts") val ts: Double,
    @SerializedName("solar_rad") val solar_rad: Double,
    @SerializedName("state_code") val state_code: String,
    @SerializedName("city_name") val city_name: String,
    @SerializedName("wind_spd") val wind_spd: Double,
    @SerializedName("last_ob_time") val last_ob_time: String,
    @SerializedName("wind_cdir_full") val wind_cdir_full: String,
    @SerializedName("wind_cdir") val wind_cdir: String,
    @SerializedName("slp") val slp: Double,
    @SerializedName("vis") val vis: Double,
    @SerializedName("h_angle") val h_angle: Int,
    @SerializedName("sunset") val sunset: String,
    @SerializedName("dni") val dni: Double,
    @SerializedName("dewpt") val dewpt: String,
    @SerializedName("snow") val snow: Int,
    @SerializedName("uv") val uv: Double,
    @SerializedName("precip") val precip: Int,
    @SerializedName("wind_dir") val wind_dir: Int,
    @SerializedName("sunrise") val sunrise: String,
    @SerializedName("ghi") val ghi: Double,
    @SerializedName("dhi") val dhi: Double,
    @SerializedName("aqi") val aqi: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("weather") val weather: Weather,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("temp") val temp: Double,
    @SerializedName("station") val station: String,
    @SerializedName("elev_angle") val elev_angle: Double,
    @SerializedName("app_temp") val app_temp: Double
)

data class Weather(
    @SerializedName("icon") val icon: String,
    @SerializedName("code") val code: String,
    @SerializedName("description") val description: String
)

fun CurrentWeatherResponse?.toCurrentWeatherData(): CurrentWeatherData? {
    if (this != null && this.data.isNotEmpty()) {
        val dataEntity = this.data.firstOrNull()
        dataEntity?.run {
            return CurrentWeatherData(
                updateTime = dataEntity.ob_time,
                temp = addCelsiusToTemp(dataEntity.temp),
                tempFeels = addCelsiusToTemp(dataEntity.app_temp),
                iconUrl = dataEntity.weather.icon,
                description = dataEntity.weather.description,
                pres = dataEntity.pres.toString(),
                humidity = addPercent(dataEntity.rh),
                windDir = dataEntity.wind_cdir_full,
                windSpeed = dataEntity.wind_spd.toString(),
                cityName = dataEntity.city_name,
                lat = dataEntity.lat,
                lon = dataEntity.lon,
                country_code = dataEntity.country_code
            )
        }
    }
    return null
}