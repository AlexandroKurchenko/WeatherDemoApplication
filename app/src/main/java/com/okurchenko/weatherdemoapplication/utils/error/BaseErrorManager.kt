package com.okurchenko.weatherdemoapplication.utils.error

import timber.log.Timber

abstract class BaseErrorManager {
    var error: GeneralError? = null

    abstract fun isIgnoreError(): Boolean

    fun printErrorMsg() {
        Timber.e(error?.getMessage())
    }
}