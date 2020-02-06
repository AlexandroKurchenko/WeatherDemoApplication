package com.okurchenko.weatherdemoapplication.ui.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.okurchenko.weatherdemoapplication.R
import com.okurchenko.weatherdemoapplication.network.receivers.NetworkCallbackListener
import com.okurchenko.weatherdemoapplication.network.receivers.NetworkChangeReceiver
import com.okurchenko.weatherdemoapplication.network.receivers.WEATHER_CONNECTION_KEY
import com.okurchenko.weatherdemoapplication.network.receivers.WeatherConnectionReceiver
import com.okurchenko.weatherdemoapplication.ui.main.fragments.main.MainFragment

class MainActivity : AppCompatActivity() {

    private val receiver = NetworkChangeReceiver()
    private val networkCallbackListener = NetworkCallbackListener()
    private val networkListenerReceiver = WeatherConnectionReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        registerConnectionListeners()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterConnectionListeners()
    }

    private fun registerConnectionListeners() {
        val filter = IntentFilter("${applicationContext.packageName}$WEATHER_CONNECTION_KEY")
        registerReceiver(networkListenerReceiver, filter)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        } else {
            networkCallbackListener.registerNetworkCallback(applicationContext)
        }
    }

    private fun unRegisterConnectionListeners() {
        unregisterReceiver(networkListenerReceiver)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            unregisterReceiver(receiver)
        } else {
            networkCallbackListener.unregisterNetworkCallback()
        }
    }
}
