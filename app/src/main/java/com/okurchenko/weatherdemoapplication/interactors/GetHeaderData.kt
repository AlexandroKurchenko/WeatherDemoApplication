package com.okurchenko.weatherdemoapplication.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData
import com.okurchenko.weatherdemoapplication.repositories.db.model.HeaderData
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder
import com.okurchenko.weatherdemoapplication.utils.error.GENERAL_ERROR_MSG
import com.okurchenko.weatherdemoapplication.utils.error.GeneralError
import javax.inject.Inject

class GetHeaderData @Inject constructor() :
    LiveDataUseCase<GetHeaderData.Params, LiveData<ResourceHolder<HeaderData>>>() {

    override fun execute(params: Params): LiveData<ResourceHolder<HeaderData>> {
        return prepareHeaderData(params.resourceHolder)
    }

    private fun prepareHeaderData(resourceHolder: ResourceHolder<CurrentWeatherData>?):
        LiveData<ResourceHolder<HeaderData>> {
        return liveData {
            if (resourceHolder?.status == ResourceHolder.DataStatus.LOADING) {
                emit(ResourceHolder.loading<HeaderData>())
            } else {
                getHeaderData(resourceHolder)?.let {
                    emit(ResourceHolder.success(it))
                } ?: run {
                    emit(ResourceHolder.error<HeaderData>(GeneralError(GENERAL_ERROR_MSG)))
                }
            }
        }
    }

    private fun getHeaderData(resourceHolder: ResourceHolder<CurrentWeatherData>?): HeaderData? {
        if (resourceHolder != null && resourceHolder.isSuccessData()) {
            val weatherData = resourceHolder.data!!
            return HeaderData(
                cityName = weatherData.cityName,
                datetime = weatherData.updateTime,
                country_code = weatherData.country_code,
                lat = weatherData.lat,
                lon = weatherData.lon
            )
        }
        return null
    }

    class Params(val resourceHolder: ResourceHolder<CurrentWeatherData>?) : LiveDataUseCase.Params()
}