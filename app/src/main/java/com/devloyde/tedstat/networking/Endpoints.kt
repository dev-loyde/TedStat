package com.devloyde.tedstat.networking

import com.devloyde.tedstat.models.*
import retrofit2.Call
import retrofit2.http.*

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

interface FeedBackEndpoints {
    @POST("formResponse")
    @FormUrlEncoded
    fun submitFeedBack(
        @Field("entry.1794988330") name: String,
        @Field("entry.1393192673") emailAddress: String,
        @Field("entry.1997933339") message: String,
        @Field("entry.1274061842") date: String
    ): Call<Void>
}
