package com.okurchenko.weatherdemoapplication.network.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import timber.log.Timber

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { notifyConnectionReceiver(context) }
            ?: run { Timber.d("Context is not available, could not notify WeatherConnectionReceiver") }
    }

    private fun notifyConnectionReceiver(context: Context) {
        val connectionListenerIntent =
            Intent("${context.packageName}$WEATHER_CONNECTION_KEY")
        connectionListenerIntent.putExtra(WEATHER_CONNECTION_KEY, getConnectionStatus(context))
        context.sendBroadcast(connectionListenerIntent)
    }

    private fun getConnectionStatus(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}