package com.okurchenko.weatherdemoapplication.di.module.main

import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import com.okurchenko.weatherdemoapplication.di.ViewModelKey
import com.okurchenko.weatherdemoapplication.repositories.HeaderImageProviderImpl
import com.okurchenko.weatherdemoapplication.repositories.ImageProvider
import com.okurchenko.weatherdemoapplication.repositories.WeatherRepository
import com.okurchenko.weatherdemoapplication.repositories.WeatherRepositoryImpl
import com.okurchenko.weatherdemoapplication.repositories.db.WeatherDatabase
import com.okurchenko.weatherdemoapplication.ui.main.fragments.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    internal abstract fun bindWeatherRepository(repository: WeatherRepositoryImpl):
        WeatherRepository

    @Binds
    internal abstract fun bindImageRepository(repository: HeaderImageProviderImpl): ImageProvider

    @Binds
    internal abstract fun bindWeatherDatabase(db: WeatherDatabase): RoomDatabase
}