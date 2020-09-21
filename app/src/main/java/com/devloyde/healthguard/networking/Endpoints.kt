package com.devloyde.healthguard.networking

import com.devloyde.healthguard.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsEndpoints {

    @GET("/api/news/recommended")
    fun getRecommendedNews(): Call<RecommendedNewsResponse>

    @GET("/api/news/local")
    fun getHealthCareNews(): Call<LocalNewsResponse>

    @GET("/api/news/global")
    fun getGlobalNews(): Call<GlobalNewsResponse>

    @GET("/api/news/country/{isocode}")
    fun getCountryNews(@Path("isocode")key: String): Call<CountryNewsResponse>

}

interface StatEndpoints {
    @GET("/api/stat/global")
    fun getGlobalStat(): Call<GlobalStatResponse>

    @GET("/api/stat/countries")
    fun getCountryStat(): Call<CountryStatResponse>

}

interface InfoEndpoints {
    @GET("/api/info/advisory")
    fun getAdvisoryInfo(): Call<AdvisoryResponse>

    @GET("/api/info/faq")
    fun getFaqInfo(): Call<FaqResponse>
}