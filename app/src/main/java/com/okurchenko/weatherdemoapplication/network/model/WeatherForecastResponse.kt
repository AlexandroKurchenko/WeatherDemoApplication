package com.okurchenko.weatherdemoapplication.network.model

import com.google.gson.annotations.SerializedName
import com.okurchenko.weatherdemoapplication.repositories.db.model.WeatherForecast

data class WeatherForecastResponse(
    @SerializedName("data") val data: List<ForecastDataEntity>,
    @SerializedName("city_name")
    val city_name: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("country_code")
    val country_code: String,
    @SerializedName("state_code")
    val state_code: String
)

data class ForecastDataEntity(
    @SerializedName("valid_date") val valid_date: String,
    @SerializedName("ts") var ts: Int,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("wind_gust_spd") val wind_gust_spd: Double,
    @SerializedName("wind_spd") val wind_spd: Double,
    @SerializedName("wind_dir") var wind_dir: Int,
    @SerializedName("wind_cdir") val wind_cdir: String,
    @SerializedName("wind_cdir_full") val wind_cdir_full: String,
    @SerializedName("temp") var temp: Double,
    @SerializedName("max_temp") var max_temp: Double,
    @SerializedName("min_temp") var min_temp: Double,
    @SerializedName("high_temp") var high_temp: Double,
    @SerializedName("low_temp") val low_temp: Double,
    @SerializedName("app_max_temp") val app_max_temp: Double,
    @SerializedName("app_min_temp") val app_min_temp: Double,
    @SerializedName("pop") var pop: Int,
    @SerializedName("precip") var precip: Double,
    @SerializedName("snow") var snow: Double,
    @SerializedName("snow_depth") var snow_depth: Double,
    @SerializedName("slp") var slp: Double,
    @SerializedName("pres") val pres: Double,
    @SerializedName("dewpt") val dewpt: Double,
    @SerializedName("rh") val rh: Double,
    @SerializedName("weather") var weather: WeatherEntity,
    @SerializedName("pod") val pod: String,
    @SerializedName("clouds_low") var clouds_low: Int,
    @SerializedName("clouds_mid") var clouds_mid: Int,
    @SerializedName("clouds_hi") var clouds_hi: Int,
    @SerializedName("clouds") var clouds: Int,
    @SerializedName("vis") var vis: Double,
    @SerializedName("max_dhi") var max_dhi: Int,
    @SerializedName("uv") var uv: Double,
    @SerializedName("moon_phase") val moon_phase: Double,
    @SerializedName("moonrise_ts") var moonrise_ts: Int,
    @SerializedName("moonset_ts") var moonset_ts: Int,
    @SerializedName("sunrise_ts") var sunrise_ts: Int,
    @SerializedName("sunset_ts") var sunset_ts: Int
)

data class WeatherEntity(
    @SerializedName("icon") val icon: String,
    @SerializedName("code") val code: String,
    @SerializedName("description") val description: String
)

fun ForecastDataEntity?.toWeatherForecast(): WeatherForecast? {
    if (this != null) {
        return WeatherForecast(
            ts = this.ts.toString(),
            temp = this.temp,
            maxTemp = this.max_temp,
            minTemp = this.min_temp,
            iconCode = this.weather.icon,
            pres = this.pres.toString(),
            humidity = this.rh.toString(),
            windDir = this.wind_cdir_full,
            windSpeed = this.wind_spd.toString()
        )
    }
    return null
}