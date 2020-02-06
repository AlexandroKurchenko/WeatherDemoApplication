package com.okurchenko.weatherdemoapplication.utils.error

import com.okurchenko.weatherdemoapplication.network.NetworkError

const val GENERAL_ERROR_MSG = "Something went wrong"
class GeneralError() {
    private var networkError: NetworkError? = null
    private var msg: String? = null

    constructor(networkError: NetworkError) : this() {
        this.networkError = networkError
    }

    constructor(msg: String) : this() {
        this.msg = msg
    }

    fun getThrowable(): Throwable? {
        return networkError?.throwable
    }

    fun getNetworkErrorCode(): Int? {
        return networkError?.code
    }

    fun getMessage(): String? {
        return if (!networkError?.msg.isNullOrBlank()) {
            networkError?.msg
        } else if (!networkError?.throwable?.localizedMessage.isNullOrBlank()) {
            networkError?.throwable?.localizedMessage
        } else if (!msg.isNullOrBlank()) {
            msg
        } else {
            GENERAL_ERROR_MSG
        }
    }
}