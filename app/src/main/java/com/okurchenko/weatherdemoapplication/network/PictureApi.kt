package com.okurchenko.weatherdemoapplication.network

import com.okurchenko.weatherdemoapplication.network.model.ImageDataResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PictureApi {

    @GET
    fun getImageByQueryAsync(@Url url: String): Deferred<Response<ImageDataResponse>>
}