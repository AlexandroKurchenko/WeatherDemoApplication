package com.okurchenko.weatherdemoapplication.repositories.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.okurchenko.weatherdemoapplication.repositories.db.model.WeatherForecast

@Dao
interface WeatherForecastDao {
    @Query("SELECT * FROM weatherforecast")
    fun getAll(): List<WeatherForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(weatherList: List<WeatherForecast>)
}