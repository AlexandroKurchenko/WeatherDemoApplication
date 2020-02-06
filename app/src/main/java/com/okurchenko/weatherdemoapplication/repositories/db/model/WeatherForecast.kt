package com.okurchenko.weatherdemoapplication.repositories.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherForecast(
    val ts: String,
    val temp: Double,
    val maxTemp: Double,
    val minTemp: Double,
    val iconCode: String,
    val pres: String,
    val humidity: String,
    val windDir: String,
    val windSpeed: String
) {
    @PrimaryKey(autoGenerate = true)
    var weatherForecastId = 0
}