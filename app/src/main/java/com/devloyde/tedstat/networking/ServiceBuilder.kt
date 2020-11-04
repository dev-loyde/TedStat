package com.devloyde.tedstat.networking

import com.devloyde.tedstat.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServiceBuilder {
    private const val baseUrl = BuildConfig.BaseUrl
    private const val feedBackBaseUrl = BuildConfig.FeedBackBaseUrl

    private val httpClient = OkHttpClient.Builder().addInterceptor(Interceptor {
       val request = it.request()
            .newBuilder().addHeader("Api-Key", BuildConfig.ApiKey).build()
        return@Interceptor it.proceed(request)
    })

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()

    fun<T> buildService(service: Class<T>):T{
        return retrofit.create(service)
    }

    private val retrofitFeedbackInstance = Retrofit.Builder()
        .baseUrl(feedBackBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()

    fun<T> buildFeedBackService(service: Class<T>):T{
        return retrofitFeedbackInstance.create(service)
    }

}
