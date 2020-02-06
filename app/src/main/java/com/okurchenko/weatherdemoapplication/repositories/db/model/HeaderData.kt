package com.okurchenko.weatherdemoapplication.repositories.db.model

import com.okurchenko.weatherdemoapplication.utils.roundToTwo

data class HeaderData(
    val cityName: String,
    val datetime: String,
    val country_code: String,
    val lat: Double,
    val lon: Double
) {
    fun getCityNameAndCode(): String = "$cityName, $country_code"
    fun getAggregatedInfo(): String = "$cityName, $country_code (${roundToTwo(lat)}, ${roundToTwo(lon)})"
}