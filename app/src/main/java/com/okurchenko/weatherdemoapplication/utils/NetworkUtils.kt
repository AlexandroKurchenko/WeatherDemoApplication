package com.okurchenko.weatherdemoapplication.utils

import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject
import javax.inject.Singleton


interface NetworkConnectionListener {
    fun registerNetworkListener(listener: ConnectionListener)
    fun unRegisterNetworkListener(listener: ConnectionListener)
}

interface NetworkConnectionNotifier {
    fun notifyNetworkState(state: Boolean)
}

@Singleton
class NetworkConnectionUtils @Inject constructor() : NetworkConnectionListener, NetworkConnectionNotifier {

    private val listeners = CopyOnWriteArrayList<ConnectionListener>()

    override fun registerNetworkListener(listener: ConnectionListener) {
        listeners.add(listener)
    }

    override fun unRegisterNetworkListener(listener: ConnectionListener) {
        listeners.remove(listener)
    }

    override fun notifyNetworkState(state: Boolean) {
        for (listener in listeners) {
            listener.onNetworkStateChanged(state)
        }
    }
}



