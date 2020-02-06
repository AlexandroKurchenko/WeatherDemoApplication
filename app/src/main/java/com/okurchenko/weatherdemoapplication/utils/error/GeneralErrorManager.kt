package com.okurchenko.weatherdemoapplication.utils.error

class GeneralErrorManager : BaseErrorManager() {
    override fun isIgnoreError(): Boolean {
        return error?.getThrowable() == null && error?.getNetworkErrorCode() != 403
    }
}