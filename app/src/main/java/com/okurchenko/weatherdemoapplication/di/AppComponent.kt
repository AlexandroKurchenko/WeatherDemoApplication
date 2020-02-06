package com.okurchenko.weatherdemoapplication.di

import android.app.Application
import com.okurchenko.weatherdemoapplication.WeatherApplication
import com.okurchenko.weatherdemoapplication.di.module.*
import com.okurchenko.weatherdemoapplication.di.view.model.FragmentBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppBindingModule::class,
        FragmentBindingModule::class,
        RepositoryDependenciesModule::class,
        NetworkConnectionModule::class,
        WeatherConnectionReceiverModule::class,
        NetworkModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<WeatherApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}