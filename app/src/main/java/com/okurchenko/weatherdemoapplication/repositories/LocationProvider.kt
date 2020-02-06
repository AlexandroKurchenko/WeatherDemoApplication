package com.okurchenko.weatherdemoapplication.repositories

import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocationProvider @Inject constructor(private val applicationContext: Context) {

    private val locationProvider: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(applicationContext)

    suspend fun requestLocation(): Location {
        return suspendCoroutine { continuation ->
            locationProvider.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        location
                        continuation.resume(location)
                    } else {
                        getLocationUpdates(continuation)
                    }
                }
                .addOnFailureListener { exception -> continuation.resumeWithException(exception) }
        }
    }

    private fun getLocationUpdates(continuation: Continuation<Location>) {
        val request = createLocationRequest()
        val locationSettingsRequestBuilder = LocationSettingsRequest.Builder().addLocationRequest(request)
        val settingsClient = LocationServices.getSettingsClient(applicationContext)
        settingsClient.checkLocationSettings(locationSettingsRequestBuilder.build())

        locationProvider.requestLocationUpdates(
            request, object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    continuation.resume(result.lastLocation)
                    locationProvider.removeLocationUpdates(this)
                }
            },
            Looper.myLooper()
        )
    }

    private fun createLocationRequest(): LocationRequest {
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 10 * 1000
        mLocationRequest.fastestInterval = 1 * 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return mLocationRequest
    }
}
