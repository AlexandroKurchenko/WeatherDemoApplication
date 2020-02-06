package com.okurchenko.weatherdemoapplication.network.model

import com.google.gson.annotations.SerializedName
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentAirQuality

data class QualityResponse(
    @SerializedName("data") val data: List<QualityData>,
    @SerializedName("city_name") val city_name: String,
    @SerializedName("lon") val lon: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("country_code") val country_code: String,
    @SerializedName("state_code") val state_code: String
)

fun QualityData.toCurrentAirQuality(): CurrentAirQuality? {
    return CurrentAirQuality(
        aqi = this.aqi,
        pm10 = this.pm10,
        pm25 = this.pm25,
        o3 = this.o3,
        so2 = this.so2,
        no2 = this.no2,
        datetime = this.datetime,
        co = this.co,
        ts = this.ts
    )
}

data class QualityData(
    @SerializedName("aqi") val aqi: Double,
    @SerializedName("pm10") val pm10: Double,
    @SerializedName("pm25") val pm25: Double,
    @SerializedName("o3") val o3: Double,
    @SerializedName("timestamp_local") val timestamp_local: String,
    @SerializedName("so2") val so2: Double,
    @SerializedName("no2") val no2: Double,
    @SerializedName("timestamp_utc") val timestamp_utc: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("co") val co: Double,
    @SerializedName("ts") val ts: Long
)