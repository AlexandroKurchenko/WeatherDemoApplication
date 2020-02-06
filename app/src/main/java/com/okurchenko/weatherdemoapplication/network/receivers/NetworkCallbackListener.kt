package com.okurchenko.weatherdemoapplication.network.receivers

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import timber.log.Timber

class NetworkCallbackListener {

    private lateinit var connectivityManager: ConnectivityManager
    private var context: Context? = null
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            notifyConnectionReceiver(true)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            notifyConnectionReceiver(false)
        }
    }

    fun registerNetworkCallback(context: Context) {
        this.context = context
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }

    fun unregisterNetworkCallback() {
        if (::connectivityManager.isInitialized) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    private fun notifyConnectionReceiver(networkAvailable: Boolean) {
        context?.let {
            val connectionListenerIntent = Intent("${it.packageName}$WEATHER_CONNECTION_KEY")
            connectionListenerIntent.putExtra(WEATHER_CONNECTION_KEY, networkAvailable)
            it.sendBroadcast(connectionListenerIntent)
        } ?: run { Timber.d("Context is not available, could not notify WeatherConnectionReceiver") }
    }
}