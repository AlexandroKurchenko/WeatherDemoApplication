package com.okurchenko.weatherdemoapplication.di.view.model

import com.okurchenko.weatherdemoapplication.di.module.main.MainModule
import com.okurchenko.weatherdemoapplication.ui.main.fragments.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeMainFragment(): MainFragment
}