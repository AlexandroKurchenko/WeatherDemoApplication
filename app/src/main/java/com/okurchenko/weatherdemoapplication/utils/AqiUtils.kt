package com.okurchenko.weatherdemoapplication.utils

fun getAqiIndex(aqi: Double): Int {
    return when {
        aqi.isGood() -> 0
        aqi.isModerate() -> 1
        aqi.isUnhealthySensitiveGroups() -> 2
        aqi.isUnhealthy() -> 3
        aqi.isVeryUnhealthy() -> 4
        aqi.isHazardous() -> 5
        else -> 0
    }
}

private fun Double.isGood(): Boolean = this > 0 && this < 50
private fun Double.isModerate(): Boolean = this > 50 && this < 100
private fun Double.isUnhealthySensitiveGroups(): Boolean = this > 100 && this < 150
private fun Double.isUnhealthy(): Boolean = this > 150 && this < 200
private fun Double.isVeryUnhealthy(): Boolean = this > 200 && this < 300
private fun Double.isHazardous(): Boolean = this > 300 && this < 500