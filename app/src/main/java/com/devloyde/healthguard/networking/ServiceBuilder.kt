package com.devloyde.healthguard.networking

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServiceBuilder {
    private const val baseUrl = "https://health-guard.herokuapp.com"

    private val httpClient = OkHttpClient.Builder().addInterceptor(Interceptor(){
       val request = it.request()
            .newBuilder().addHeader("Subscription-Key","dcb377dcaa654d42852d79dd0b977247").build()
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


}
