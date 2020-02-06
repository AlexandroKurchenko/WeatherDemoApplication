package com.okurchenko.weatherdemoapplication.interactors


import androidx.lifecycle.LiveData
import com.okurchenko.weatherdemoapplication.repositories.WeatherRepository
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder
import com.okurchenko.weatherdemoapplication.utils.error.BaseErrorManager
import com.okurchenko.weatherdemoapplication.utils.error.ResourceMapperMediatorLiveData
import javax.inject.Inject

class GetWeather @Inject constructor(
    private val weatherRepository: WeatherRepository
) : LiveDataUseCase<GetWeather.Params, LiveData<ResourceHolder<CurrentWeatherData>>>() {

    class Params(
        val lat: Double,
        val lon: Double,
        val errorManager: BaseErrorManager
    ) : LiveDataUseCase.Params()

    override fun execute(params: Params): LiveData<ResourceHolder<CurrentWeatherData>> {
        val mappedWeatherData = ResourceMapperMediatorLiveData<CurrentWeatherData>(params.errorManager)
        val weatherSource = weatherRepository.fetchWeather(params.lat, params.lon)
        mappedWeatherData.addCustomSource(weatherSource)
        return mappedWeatherData
    }
}