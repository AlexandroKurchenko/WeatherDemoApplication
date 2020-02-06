package com.okurchenko.weatherdemoapplication.repositories

import androidx.lifecycle.LiveData
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentAirQuality
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData
import com.okurchenko.weatherdemoapplication.repositories.db.model.WeatherForecast
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder

interface WeatherRepository {

    fun fetchAirQuality(lat: Double, lon: Double): LiveData<ResourceHolder<List<CurrentAirQuality>>>
    fun fetchWeather(lat: Double, lon: Double): LiveData<ResourceHolder<CurrentWeatherData>>
    fun fetchDailyWeatherForecast(lat: Double, lon: Double): LiveData<ResourceHolder<List<WeatherForecast>>>
}