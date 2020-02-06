package com.okurchenko.weatherdemoapplication.interactors

import androidx.lifecycle.LiveData
import com.okurchenko.weatherdemoapplication.repositories.WeatherRepository
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentAirQuality
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder
import com.okurchenko.weatherdemoapplication.utils.error.BaseErrorManager
import com.okurchenko.weatherdemoapplication.utils.error.ResourceMapperMediatorLiveData
import javax.inject.Inject

class GetAirQuality @Inject constructor(
    private val weatherRepository: WeatherRepository
) : LiveDataUseCase<GetAirQuality.Params, LiveData<ResourceHolder<List<CurrentAirQuality>>>>() {

    class Params(
        val lat: Double,
        val lon: Double,
        val errorManager: BaseErrorManager
    ) : LiveDataUseCase.Params()

    override fun execute(params: Params): LiveData<ResourceHolder<List<CurrentAirQuality>>> {
        val mappedAirQuality = ResourceMapperMediatorLiveData<List<CurrentAirQuality>>(params.errorManager)
        val airQualitySource = weatherRepository.fetchAirQuality(params.lat, params.lon)
        mappedAirQuality.addCustomSource(airQualitySource)
        return mappedAirQuality
    }
}