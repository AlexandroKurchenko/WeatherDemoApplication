package com.okurchenko.weatherdemoapplication.di.module

import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(includes = [AndroidInjectionModule::class])
abstract class AndroidSupportInjectionModule private constructor()