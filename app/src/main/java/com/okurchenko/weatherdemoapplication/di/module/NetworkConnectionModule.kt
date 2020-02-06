package com.okurchenko.weatherdemoapplication.di.module

import com.okurchenko.weatherdemoapplication.utils.NetworkConnectionListener
import com.okurchenko.weatherdemoapplication.utils.NetworkConnectionNotifier
import com.okurchenko.weatherdemoapplication.utils.NetworkConnectionUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkConnectionModule {

    @Provides
    @Singleton
    fun provideNetworkConnectionUtils(): NetworkConnectionUtils {
        return NetworkConnectionUtils()
    }

    @Provides
    fun provideNetworkConnectionListener(connectionUtils: NetworkConnectionUtils): NetworkConnectionListener {
        return connectionUtils
    }

    @Provides
    fun provideNetworkConnectionNotifier(connectionUtils: NetworkConnectionUtils): NetworkConnectionNotifier {
        return connectionUtils
    }
}