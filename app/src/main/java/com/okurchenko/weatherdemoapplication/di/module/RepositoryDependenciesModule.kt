package com.okurchenko.weatherdemoapplication.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.okurchenko.weatherdemoapplication.repositories.db.WeatherDatabase
import dagger.Module
import dagger.Provides

@Module
class RepositoryDependenciesModule {

    @Provides
    fun provideDatabase(context: Context): WeatherDatabase {
        return Room.databaseBuilder(context, WeatherDatabase::class.java, "weather").build()
    }

    @Provides
    fun provideSharePref(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}