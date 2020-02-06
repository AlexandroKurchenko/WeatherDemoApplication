package com.okurchenko.weatherdemoapplication.utils

interface ConnectionListener {
    fun onNetworkStateChanged(connectionAvailable: Boolean)
}