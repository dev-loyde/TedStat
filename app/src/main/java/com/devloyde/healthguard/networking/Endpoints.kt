package com.devloyde.healthguard.networking

import com.devloyde.healthguard.models.News

import com.devloyde.healthguard.models.WhoRssFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsEndpoints {

    @GET("/coronavirus/news/{location}")
    fun getNews(@Path("location")key: String): Call<News>

    @GET("/rss-feeds/news-english.xml")
    fun getWhoFeed(): Call<WhoRssFeed>
}
