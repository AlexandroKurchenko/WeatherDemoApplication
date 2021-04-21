package com.okurchenko.weatherdemoapplication.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import com.okurchenko.weatherdemoapplication.BuildConfig
import com.okurchenko.weatherdemoapplication.network.PictureApi
import com.okurchenko.weatherdemoapplication.network.model.ImageDataResponse
import com.okurchenko.weatherdemoapplication.repositories.base.BaseNetworkRepository
import com.okurchenko.weatherdemoapplication.repositories.base.NetworkResult
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentWeatherData
import com.okurchenko.weatherdemoapplication.utils.ResourceHolder
import com.okurchenko.weatherdemoapplication.utils.error.GeneralError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class HeaderImageProviderImpl @Inject constructor(
    private val api: PictureApi
) : BaseNetworkRepository(), ImageProvider {

    override fun getImageByQueryAsync(
        resourceHolder: ResourceHolder<CurrentWeatherData>?,
        minWidth: Int,
        minHeight: Int
    ): LiveData<ResourceHolder<String>> {
        return liveData(context = Dispatchers.IO) {
            if (resourceHolder?.status == ResourceHolder.DataStatus.LOADING) {
                emit(ResourceHolder.loading<String>())
            } else {
                resourceHolder?.data?.let {
                    val url = getImageUrl(it.cityName, minWidth, minHeight)
                    when (val imageResponse = safeApiCall { api.getImageByQueryAsync(url).await() }) {
                        is NetworkResult.Success -> prepareResult(this, imageResponse)
                        is NetworkResult.Error -> emit(ResourceHolder.error<String>(GeneralError(imageResponse.networkError)))
                    }
                } ?: run {
                    emit(ResourceHolder.error<String>(GeneralError("City name is not defined")))
                }
            }
        }
    }

    private suspend fun prepareResult(
        liveDataScope: LiveDataScope<ResourceHolder<String>>,
        imageResponse: NetworkResult.Success<ImageDataResponse>
    ) {
        return withContext(Dispatchers.Default) {
            ImageMapper.mapResultToImageUrl(imageResponse.data)?.let {
                liveDataScope.emit(ResourceHolder.success(it))
            } ?: run {
                liveDataScope.emit(ResourceHolder.error(GeneralError("No image found")))
            }
        }
    }

    private fun getImageUrl(
        queryParam: String,
        minWidth: Int,
        minHeight: Int
    ): String {
        return "${BuildConfig.IMAGE_BASE_URL}?key=${BuildConfig.IMAGE_API_KEY}&q=$queryParam&safesearch=true" +
            "&orientation=horizontal&page=1&per_page=3&min_width=$minWidth&min_height=$minHeight"
    }

    private object ImageMapper {
        fun mapResultToImageUrl(result: ImageDataResponse?): String? {
            result?.run {
                val imageResults = result.hits
                val index = Random.nextInt(0, imageResults.size)
                return if (imageResults.size > index) {
                    imageResults[index].webFormatURL
                } else {
                    imageResults[0].webFormatURL
                }
            }
            return null
        }
    }
}