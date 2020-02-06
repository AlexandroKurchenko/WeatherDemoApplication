package com.okurchenko.weatherdemoapplication.interactors

import androidx.lifecycle.LiveData
import com.okurchenko.weatherdemoapplication.repositories.WeatherRepository
import com.okurchenko.weatherdemoapplication.repositories.db.model.WeatherForecast
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder
import com.okurchenko.weatherdemoapplication.utils.error.BaseErrorManager
import com.okurchenko.weatherdemoapplication.utils.error.ResourceMapperMediatorLiveData
import javax.inject.Inject

class GetWeatherForecast @Inject constructor(
    private val weatherRepository: WeatherRepository

) : LiveDataUseCase<GetWeatherForecast.Params, LiveData<ResourceHolder<List<WeatherForecast>>>>() {
    class Params(
        val lat: Double,
        val lon: Double,
        val errorManager: BaseErrorManager
    ) : LiveDataUseCase.Params()

    override fun execute(params: Params): LiveData<ResourceHolder<List<WeatherForecast>>> {
        val mappedWeatherForecast =
            ResourceMapperMediatorLiveData<List<WeatherForecast>>(
                params.errorManager
            )
        val weatherForecastSource = weatherRepository.fetchDailyWeatherForecast(params.lat, params.lon)
        mappedWeatherForecast.addCustomSource(weatherForecastSource)
        return mappedWeatherForecast
    }
}