package com.okurchenko.weatherdemoapplication.network

//class CacheInterceptor @Inject constructor(private val networkConnection: NetworkConnection) :
//    Interceptor {
//    private val fiveSeconds = 5
//    private val sevenDays = 60 * 60 * 24 * 7
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        var request = chain.request()
//        request = if (networkConnection.isOnline) {
//            request.newBuilder().header("Cache-Control", "public, max-age=$fiveSeconds").build()
//        } else {
//            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=$sevenDays").build()
//        }
//        return chain.proceed(request)
//    }
//}