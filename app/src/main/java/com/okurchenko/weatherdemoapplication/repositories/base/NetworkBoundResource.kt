package com.okurchenko.weatherdemoapplication.repositories.base

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder
import com.okurchenko.weatherdemoapplication.utils.error.GeneralError
import kotlinx.coroutines.Dispatchers

const val EMPTY_STORAGE_DATA: String = "EMPTY_STORAGE_DATA"

abstract class NetworkBoundResource<StorageType, NetworkResponseType : Any> {

    fun resultAsLiveData(): LiveData<ResourceHolder<StorageType>> = result

    @WorkerThread
    protected abstract fun fetchData(): StorageType?

    @WorkerThread
    protected abstract fun isStorageDataOld(storageData: StorageType?): Boolean

    @WorkerThread
    protected abstract suspend fun networkCall(): NetworkResult<NetworkResponseType>

    @WorkerThread
    protected abstract fun mapResponse(response: NetworkResponseType?): StorageType?

    @WorkerThread
    protected abstract fun saveResult(result: StorageType)

    private val result = liveData<ResourceHolder<StorageType>>(context = Dispatchers.IO) {
        emit(ResourceHolder.loading())
        val storageData = fetchData()
        if (isStorageDataOld(storageData)) {
            when (val networkResponse = networkCall()) {
                is NetworkResult.Success<*> -> processNetworkData(this, networkResponse.data)
                is NetworkResult.Error -> emit(ResourceHolder.error(GeneralError(networkResponse.networkError)))
            }
        } else {
            processStorageData(this, storageData)
        }
    }

    @WorkerThread
    private suspend fun processNetworkData(
        liveDataScope: LiveDataScope<ResourceHolder<StorageType>>,
        networkData: Any
    ) {
        @Suppress("UNCHECKED_CAST")
        val convertedData = mapResponse(networkData as? NetworkResponseType)
        if (convertedData == null) {
            liveDataScope.emit(ResourceHolder.error(GeneralError(EMPTY_STORAGE_DATA)))
        } else {
            saveResult(convertedData)
            liveDataScope.emit(ResourceHolder.success(convertedData))
        }
    }

    @WorkerThread
    private suspend fun processStorageData(
        liveDataScope: LiveDataScope<ResourceHolder<StorageType>>,
        storageData: StorageType?
    ) = storageData?.run { liveDataScope.emit(ResourceHolder.success(storageData)) }
}