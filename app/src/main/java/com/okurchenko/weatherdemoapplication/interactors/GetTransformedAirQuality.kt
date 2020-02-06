package com.okurchenko.weatherdemoapplication.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentAirQuality
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder
import com.okurchenko.weatherdemoapplication.utils.error.GeneralError
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetTransformedAirQuality @Inject constructor() :
    LiveDataUseCase<GetTransformedAirQuality.Params, LiveData<ResourceHolder<Any>>>() {

    enum class Action {
        GET_FIRST, GET_EXCEPT_FIRST
    }

    companion object {
        const val NO_DATA_FOUND = "Unfortunately no air quality found"
    }

    class Params(val airQualities: ResourceHolder<List<CurrentAirQuality>>, val action: Action) :
        LiveDataUseCase.Params()

    override fun execute(params: Params): LiveData<ResourceHolder<Any>> =
        liveData(context = Dispatchers.Default) {
            when (params.airQualities.status) {
                ResourceHolder.DataStatus.LOADING -> emit(ResourceHolder.loading())
                ResourceHolder.DataStatus.SUCCESS -> emit(getItemByAction(params.action, params.airQualities.data))
                ResourceHolder.DataStatus.ERROR -> emit(ResourceHolder.error(params.airQualities.error!!))
            }
        }

    private fun getItemByAction(
        action: Action,
        airQuality: List<CurrentAirQuality>?
    ): ResourceHolder<Any> = when (action) {
        Action.GET_FIRST -> getFirstItem(airQuality)
        Action.GET_EXCEPT_FIRST -> getExceptFirstItem(airQuality)
    }

    private fun getFirstItem(airQuality: List<CurrentAirQuality>?): ResourceHolder<Any> {
        val item = airQuality?.firstOrNull()
        return if (item != null) {
            ResourceHolder.success(item)
        } else {
            ResourceHolder.error(GeneralError(NO_DATA_FOUND))
        }
    }

    private fun getExceptFirstItem(airQuality: List<CurrentAirQuality>?): ResourceHolder<Any> {
        return if (airQuality != null && airQuality.size > 1) {
            ResourceHolder.success(airQuality.subList(1, airQuality.size - 1))
        } else {
            ResourceHolder.error(GeneralError(NO_DATA_FOUND))
        }
    }
}