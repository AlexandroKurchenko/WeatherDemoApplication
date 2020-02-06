package com.okurchenko.weatherdemoapplication.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.okurchenko.weatherdemoapplication.di.view.model.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class AppBindingModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}