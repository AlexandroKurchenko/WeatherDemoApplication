package com.okurchenko.weatherdemoapplication.utils

import com.okurchenko.weatherdemoapplication.utils.error.GeneralError

class ResourceHolder<T> private constructor(
    val data: T? = null,
    val status: DataStatus,
    val error: GeneralError? = null
) {

    companion object {
        fun <T> loading(): ResourceHolder<T> = ResourceHolder(status = DataStatus.LOADING)
        fun <T> success(data: T): ResourceHolder<T> =
            ResourceHolder(data = data, status = DataStatus.SUCCESS)

        fun <T> error(error: GeneralError): ResourceHolder<T> =
            ResourceHolder(error = error, status = DataStatus.ERROR)
    }

    enum class DataStatus {
        LOADING, ERROR, SUCCESS
    }

    fun isSuccessData(): Boolean = status == DataStatus.SUCCESS && data != null
    fun isDone(): Boolean = status == DataStatus.SUCCESS || status == DataStatus.ERROR
}