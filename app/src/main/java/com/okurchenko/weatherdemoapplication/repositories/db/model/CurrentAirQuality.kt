package com.okurchenko.weatherdemoapplication.repositories.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentAirQuality(
    val aqi: Double,
    val pm10: Double,
    val pm25: Double,
    val o3: Double,
    val so2: Double,
    val no2: Double,
    val datetime: String,
    val co: Double,
    val ts: Long
){
    @PrimaryKey(autoGenerate = true)
    var airQualityId = 0
}