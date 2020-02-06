package com.okurchenko.weatherdemoapplication.interactors

import android.location.Location
import com.okurchenko.weatherdemoapplication.repositories.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDeviceLocation @Inject constructor(
    private val locationProvider: LocationProvider
) : NoParamsUseCase<Location>() {

    override suspend fun execute(): Location = withContext(Dispatchers.IO) {
        locationProvider.requestLocation()
    }
}