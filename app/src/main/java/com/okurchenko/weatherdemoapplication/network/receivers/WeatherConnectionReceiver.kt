package com.okurchenko.weatherdemoapplication.network.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.okurchenko.weatherdemoapplication.utils.NetworkConnectionNotifier
import dagger.android.AndroidInjection
import javax.inject.Inject

const val WEATHER_CONNECTION_KEY = ".WEATHER_CONNECTION_KEY"

class WeatherConnectionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var connectionNotifier: NetworkConnectionNotifier

    override fun onReceive(context: Context?, intent: Intent?) {
        val actionFilter = "${context?.packageName}$WEATHER_CONNECTION_KEY"
        if (intent != null && intent.action.equals(actionFilter) && intent.hasExtra(WEATHER_CONNECTION_KEY)) {
            AndroidInjection.inject(this, context)
            val networkStatus = intent.getBooleanExtra(WEATHER_CONNECTION_KEY, false)
            connectionNotifier.notifyNetworkState(networkStatus)
        }
    }
}