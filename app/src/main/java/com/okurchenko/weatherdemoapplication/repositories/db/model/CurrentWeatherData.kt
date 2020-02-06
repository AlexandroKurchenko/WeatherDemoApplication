package com.okurchenko.weatherdemoapplication.repositories.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentWeatherData(
    val updateTime: String,
    val temp: String,
    val tempFeels: String,
    val iconUrl: String,
    val description: String,
    val pres: String,
    val humidity: String,
    val windDir: String,
    val windSpeed: String,
    val cityName: String,
    val lat: Double,
    val lon: Double,
    val country_code: String
){
    @PrimaryKey(autoGenerate = true)
    var weatherId = 0
}