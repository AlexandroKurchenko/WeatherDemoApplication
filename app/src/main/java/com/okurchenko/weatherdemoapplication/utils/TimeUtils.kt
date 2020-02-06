package com.okurchenko.weatherdemoapplication.utils

import com.okurchenko.weatherdemoapplication.BuildConfig
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

fun Long.transformedDateTime(locale: Locale): String? {
    val timeStampInMs = this * 1000
    val dateFormat = SimpleDateFormat("E, d h:mm a", locale)
    return dateFormat.format(Date(timeStampInMs))
}

fun Long.diffTimeInMins(): Long = TimeUnit.MILLISECONDS.toMinutes(abs(getNowTime() - Date(this).time))

fun getNowTime(): Long = Date(System.currentTimeMillis()).time

fun addSleepIfDebug() {
    if (BuildConfig.DEBUG) Thread.sleep(2000)
}