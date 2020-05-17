package com.devloyde.healthguard.respositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.devloyde.healthguard.models.*
import com.devloyde.healthguard.networking.NetworkServiceBuilder
import com.devloyde.healthguard.networking.NewsEndpoints
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

object NewsRepository {

    private val newsExecutors = Executors.newFixedThreadPool(4)

    fun getRecommendedNews(page: Int): MutableLiveData<List<RecommendedNews>> {
        val recommendedNews = MutableLiveData<List<RecommendedNews>>()
        newsExecutors.execute {
            val request = NetworkServiceBuilder.buildService(NewsEndpoints::class.java)
            val call = request.getRecommendedNews(page)

            call.enqueue(object : Callback<RecommendedNewsResponse> {
                override fun onFailure(call: Call<RecommendedNewsResponse>, t: Throwable) {
                    Log.d("RECOMMENDED NEWS","Error fetching recommended news")
                }

                override fun onResponse(
                    call: Call<RecommendedNewsResponse>,
                    response: Response<RecommendedNewsResponse>
                ) {
                    if (response.isSuccessful && !response.body()?.error!!) {
                        recommendedNews.value = response.body()!!.data
                        Log.d("RECOMMENDED NEWS","Success fetching recommended news")
                    }
                }
            })

        }
        return recommendedNews
    }

    fun getLocalNews(): MutableLiveData<List<LocalNews>> {
        val localNews = MutableLiveData<List<LocalNews>>()
        newsExecutors.execute {
            val request = NetworkServiceBuilder.buildService(NewsEndpoints::class.java)
            val call = request.getHealthCareNews()

            call.enqueue(object : Callback<LocalNewsResponse> {
                override fun onFailure(call: Call<LocalNewsResponse>, t: Throwable) {
                    Log.d("LOCAL NEWS","Error fetching local news")
                }

                override fun onResponse(
                    call: Call<LocalNewsResponse>,
                    response: Response<LocalNewsResponse>
                ) {
                    if (response.isSuccessful && !response.body()?.error!!) {
                        Log.d("LOCAL NEWS","Success fetching local news")
                        localNews.value = response.body()!!.data
                    }
                }
            })

        }
        return localNews
    }

    fun getGlobalNews(): MutableLiveData<List<GlobalNews>> {
        val globalNews = MutableLiveData<List<GlobalNews>>()
        newsExecutors.execute {
            val request = NetworkServiceBuilder.buildService(NewsEndpoints::class.java)
            val call = request.getGlobalNews()

            call.enqueue(object : Callback<GlobalNewsResponse> {
                override fun onFailure(call: Call<GlobalNewsResponse>, t: Throwable) {
                    Log.d("GLOBAL NEWS","Error fetching global news")
                }

                override fun onResponse(
                    call: Call<GlobalNewsResponse>,
                    response: Response<GlobalNewsResponse>
                ) {
                    if (response.isSuccessful && !response.body()!!.error) {
                        Log.d("GLOBAL NEWS","Success fetching global news")
                        globalNews.value = response.body()!!.data.news
                    }
                }
            })

        }
        return globalNews
    }

    fun getCountryNews(countryIso: String): MutableLiveData<List<Any>> {
        val countryNews = MutableLiveData<List<Any>>()
        newsExecutors.execute {
            val request = NetworkServiceBuilder.buildService(NewsEndpoints::class.java)
            val call = request.getCountryNews(countryIso)

            call.enqueue(object : Callback<NigeriaNewsResponse> {
                override fun onFailure(call: Call<NigeriaNewsResponse>, t: Throwable) {
                    Log.d("COUNTRY NEWS","Error fetching country news")
                }

                override fun onResponse(
                    call: Call<NigeriaNewsResponse>,
                    response: Response<NigeriaNewsResponse>
                ) {
                    if (response.isSuccessful && !response.body()!!.error) {
                        countryNews.value = response.body()!!.data
                    }
                }
            })

        }
        return countryNews

    }

}