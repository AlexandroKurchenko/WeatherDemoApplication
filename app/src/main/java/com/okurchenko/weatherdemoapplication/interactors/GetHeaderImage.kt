package com.okurchenko.weatherdemoapplication.interactors

import androidx.lifecycle.LiveData
import com.okurchenko.weatherdemoapplication.repositories.ImageProvider
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder
import javax.inject.Inject

class GetHeaderImage @Inject constructor(
    private val provider: ImageProvider
) : LiveDataUseCase<GetHeaderImage.Params, LiveData<ResourceHolder<String>>>() {

    class Params(val resourceHolder: ResourceHolder<CurrentWeatherData>?, val minWidth: Int, val minHeight: Int) : LiveDataUseCase.Params()

    override fun execute(params: Params): LiveData<ResourceHolder<String>> =
        provider.getImageByQueryAsync(params.resourceHolder, params.minWidth, params.minHeight)
}