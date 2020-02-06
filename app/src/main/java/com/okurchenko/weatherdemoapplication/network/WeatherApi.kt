package com.okurchenko.weatherdemoapplication.network

import com.okurchenko.weatherdemoapplication.network.model.CurrentWeatherResponse
import com.okurchenko.weatherdemoapplication.network.model.QualityResponse
import com.okurchenko.weatherdemoapplication.network.model.WeatherForecastResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("forecast/daily")
    fun weatherForecastAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") key: String
    ): Deferred<Response<WeatherForecastResponse>>

    @GET("current")
    fun currentWeatherByLocationAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") key: String
    ): Deferred<Response<CurrentWeatherResponse>>

    @GET("forecast/airquality")
    fun airQualityAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") key: String
    ): Deferred<Response<QualityResponse>>

}