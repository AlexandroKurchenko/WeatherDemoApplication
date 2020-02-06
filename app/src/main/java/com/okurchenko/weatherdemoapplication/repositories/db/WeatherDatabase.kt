package com.okurchenko.weatherdemoapplication.repositories.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentAirQuality
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData
import com.okurchenko.weatherdemoapplication.repositories.db.model.WeatherForecast

@Database(entities = [CurrentAirQuality::class, CurrentWeatherData::class, WeatherForecast::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentAirQualityDao(): CurrentAirQualityDao
    abstract fun currentWeatherDataDao(): CurrentWeatherDataDao
    abstract fun weatherForecastDao(): WeatherForecastDao
}