package com.okurchenko.weatherdemoapplication.repositories.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentAirQuality

@Dao
interface CurrentAirQualityDao {
    @Query("SELECT * FROM currentairquality")
    fun getAll(): List<CurrentAirQuality>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAirQuality(airQuality: List<CurrentAirQuality>)
}