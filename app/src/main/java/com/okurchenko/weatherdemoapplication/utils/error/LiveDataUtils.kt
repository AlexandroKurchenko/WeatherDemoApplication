package com.okurchenko.weatherdemoapplication.utils.error

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder

class ResourceMapperMediatorLiveData<T>(private val errorManager: BaseErrorManager) :
    MediatorLiveData<ResourceHolder<T>>() {

    fun addCustomSource(source: LiveData<ResourceHolder<T>>) {
        super.addSource(source) { resource ->
            if (resource?.status != ResourceHolder.DataStatus.ERROR) {
                this.value = resource
            } else {
                checkErrorStatus(resource)
            }
        }
    }

    private fun checkErrorStatus(resourceHolder: ResourceHolder<T>) {
        errorManager.error = resourceHolder.error
        if (errorManager.isIgnoreError()) {
            errorManager.printErrorMsg()
        } else {
            this.value = resourceHolder
        }
    }
}

class AutoRemoveCompleteSourceMediatorLiveData<T> : MediatorLiveData<ResourceHolder<T>>() {
    fun addCustomSource(source: LiveData<ResourceHolder<T>>) {
        super.addSource(source) {
            this.value = it
            if (it.isDone()) removeSource(source)

        }
    }
}