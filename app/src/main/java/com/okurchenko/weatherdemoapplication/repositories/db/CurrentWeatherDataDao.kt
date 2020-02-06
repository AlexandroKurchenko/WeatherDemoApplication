package com.okurchenko.weatherdemoapplication.repositories.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData

@Dao
interface CurrentWeatherDataDao {
    @Query("SELECT * FROM currentweatherdata")
    fun getAll(): CurrentWeatherData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherData: CurrentWeatherData)
}