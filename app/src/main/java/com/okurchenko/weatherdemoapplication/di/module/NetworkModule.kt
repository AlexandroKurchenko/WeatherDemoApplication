package com.okurchenko.weatherdemoapplication.di.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.okurchenko.weatherdemoapplication.BuildConfig
import com.okurchenko.weatherdemoapplication.network.PictureApi
import com.okurchenko.weatherdemoapplication.network.WeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {



    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .callFactory(client)
            .build()
    }

    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    fun providePictureApi(retrofit: Retrofit): PictureApi {
        return retrofit.create(PictureApi::class.java)
    }

//    @Provides
//    fun provideCache(context: Context): Cache {
//        val cacheSize = 10 * 1024 * 1024
//        return Cache(context.cacheDir, cacheSize.toLong())
//    }
//
//    @Provides
//    fun provideCacheInterceptor(networkConnection: NetworkConnection): CacheInterceptor {
//        return CacheInterceptor(networkConnection)
//    }
//
//    @Provides
//    fun provideOkHttpClient(
//        cache: Cache,
//        cacheInterceptor: CacheInterceptor
//    ): OkHttpClient {
//        return OkHttpClient.Builder()
//            .cache(cache)
//            .addInterceptor(cacheInterceptor)
//            .connectTimeout(15, TimeUnit.SECONDS)
//            .readTimeout(15, TimeUnit.SECONDS)
//            .build()
//    }
}