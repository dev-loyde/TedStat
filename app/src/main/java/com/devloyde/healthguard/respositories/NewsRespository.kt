package com.devloyde.healthguard.respositories

import android.util.Log
import androidx.lifecycle.LiveData

import com.devloyde.healthguard.db.NewsDao
import com.devloyde.healthguard.models.*
import com.devloyde.healthguard.networking.NetworkServiceBuilder
import com.devloyde.healthguard.networking.NewsEndpoints
import com.devloyde.healthguard.ui.news.NewsFragment.Companion.COUNTRY_NEWS
import com.devloyde.healthguard.ui.news.NewsFragment.Companion.GLOBAL_NEWS
import com.devloyde.healthguard.ui.news.NewsFragment.Companion.LOCAL_NEWS
import com.devloyde.healthguard.ui.news.NewsFragment.Companion.RECOMMENDED_NEWS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NewsRespository(
    val newsDao: NewsDao,
    val newsExecutors: ExecutorService
) {

    private val request = NetworkServiceBuilder.buildService(NewsEndpoints::class.java)


    fun getRecommendedNews(): LiveData<List<RecommendedNews>> {
        newsExecutors.execute {
            Log.d("RECOMMENDED NEWS", "checking db for recommended news")
            val timeout = newsDao.checkTimeout(RECOMMENDED_NEWS)
            if (timeout == null || timeout.timeout < System.currentTimeMillis()) {
                Log.d("RECOMMENDED NEWS", "Not available in Db recommended news")
                    newsDao.deleteTimeout(RECOMMENDED_NEWS)
                    Log.d("RECOMMENDED NEWS", "Delete old recommended news timeout")
                    val request = NetworkServiceBuilder.buildService(NewsEndpoints::class.java)
                    val call = request.getRecommendedNews()

                    call.enqueue(object : Callback<RecommendedNewsResponse> {
                        override fun onFailure(call: Call<RecommendedNewsResponse>, t: Throwable) {
                            Log.d("RECOMMENDED NEWS", "Error fetching recommended news")
                        }

                        override fun onResponse(
                            call: Call<RecommendedNewsResponse>,
                            response: Response<RecommendedNewsResponse>
                        ) {
                            if (response.isSuccessful && !response.body()?.error!!) {
                                Log.d("RECOMMENDED NEWS", "Success fetching recommended news")
                                newsExecutors.execute {
                                    Log.d(
                                        "RECOMMENDED NEWS",
                                        "Success saving recommended news to db"
                                    )
                                    newsDao.saveRecommendedNews(*response.body()!!.data.toTypedArray())
                                    Log.d("RECOMMENDED NEWS", "Saving recommended news timeout")
                                    newsDao.saveTimeout(
                                        TimeoutCheck(
                                            name = RECOMMENDED_NEWS
                                        )
                                    )
                                }
                            }
                        }
                    })
                }

        }
        return newsDao.loadRecommendedNews()
    }


    fun getLocalNews(): LiveData<List<LocalNews>> {
        newsExecutors.execute {
            val timeout = newsDao.checkTimeout(LOCAL_NEWS)
            if (timeout == null || timeout.timeout < System.currentTimeMillis()) {
                val call = request.getHealthCareNews()
                call.enqueue(object : Callback<LocalNewsResponse> {
                    override fun onFailure(call: Call<LocalNewsResponse>, t: Throwable) {
                        Log.d("LOCAL NEWS", "Error fetching local news")
                    }

                    override fun onResponse(
                        call: Call<LocalNewsResponse>,
                        response: Response<LocalNewsResponse>
                    ) {
                        if (response.isSuccessful && !response.body()?.error!!) {
                            Log.d("LOCAL NEWS", "Success fetching local news")
                            newsExecutors.execute {
                                newsDao.saveLocalNews(*response.body()!!.data.toTypedArray())
                                newsDao.saveTimeout(
                                    TimeoutCheck(
                                        name = LOCAL_NEWS
                                    )
                                )
                            }
                        }
                    }
                })
            }
        }
        return newsDao.loadLocalNews()
    }

    fun getGlobalNews(): LiveData<List<GlobalNews>> {
        newsExecutors.execute {
            val timeout = newsDao.checkTimeout(GLOBAL_NEWS)
            if (timeout == null || timeout.timeout < System.currentTimeMillis()) {
                val call = request.getGlobalNews()
                call.enqueue(object : Callback<GlobalNewsResponse> {
                    override fun onFailure(call: Call<GlobalNewsResponse>, t: Throwable) {
                        Log.d(
                            "GLOBAL NEWS",
                            "Error fetching global news ${t.cause} ${t.stackTrace}"
                        )
                    }

                    override fun onResponse(
                        call: Call<GlobalNewsResponse>,
                        response: Response<GlobalNewsResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("GLOBAL NEWS", "Success fetching global news")
                            newsExecutors.execute {
                                newsDao.saveGlobalNews(*response.body()!!.data.toTypedArray())
                                newsDao.saveTimeout(
                                    TimeoutCheck(
                                        name = GLOBAL_NEWS
                                    )
                                )
                            }
                        }
                    }

                })
            }
        }
        return newsDao.loadGlobalNews()
    }

    fun getCountryNews(countryIso: String):LiveData<List<CountryNews>>  {
        newsExecutors.execute {
            val timeout = newsDao.checkTimeout(COUNTRY_NEWS)
            if (timeout == null || timeout.timeout < System.currentTimeMillis()) {
                val call = request.getCountryNews(countryIso)

                call.enqueue(object : Callback<CountryNewsResponse> {
                    override fun onFailure(call: Call<CountryNewsResponse>, t: Throwable) {
                        Log.d("COUNTRY NEWS", "Error fetching country news")
                    }

                    override fun onResponse(
                        call: Call<CountryNewsResponse>,
                        response: Response<CountryNewsResponse>
                    ) {
                        if (response.isSuccessful && !response.body()!!.error) {
                            newsExecutors.execute {
                                newsDao.saveCountryNews(*response.body()!!.data.toTypedArray())
                                newsDao.saveTimeout(
                                    TimeoutCheck(
                                        name = COUNTRY_NEWS
                                    )
                                )
                            }
                        }
                    }
                })
            }
        }
        return newsDao.loadCountryNews()
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var NEWSRESPOSITORYINSTANCE: NewsRespository? = null

        fun getNewsRepository(
            newsDao: NewsDao,
            newsExecutor: ExecutorService
        ): NewsRespository {
            val tempInstance = NEWSRESPOSITORYINSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = NewsRespository(newsDao, newsExecutor)
                NEWSRESPOSITORYINSTANCE = instance
                return instance
            }
        }
    }
}