package com.okurchenko.weatherdemoapplication.repositories

import androidx.lifecycle.LiveData
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder

interface ImageProvider {
    fun getImageByQueryAsync(resourceHolder: ResourceHolder<CurrentWeatherData>?, minWidth: Int, minHeight: Int): LiveData<ResourceHolder<String>>
}