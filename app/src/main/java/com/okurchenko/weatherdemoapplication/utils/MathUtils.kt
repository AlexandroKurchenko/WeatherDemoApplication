package com.okurchenko.weatherdemoapplication.utils

import java.math.RoundingMode

fun roundToTwo(input: Double): Double {
    return input.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble()
}