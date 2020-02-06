package com.okurchenko.weatherdemoapplication.di.module

import com.okurchenko.weatherdemoapplication.network.receivers.WeatherConnectionReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WeatherConnectionReceiverModule {
    @ContributesAndroidInjector
    internal abstract fun contributeWeatherConnectionReceiver(): WeatherConnectionReceiver
}