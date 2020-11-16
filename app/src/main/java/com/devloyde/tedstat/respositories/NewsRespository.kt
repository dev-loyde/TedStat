package com.devloyde.tedstat.respositories

import android.util.Log

import com.devloyde.tedstat.db.NewsDao
import com.devloyde.tedstat.interfaces.NewsRespositoryInterface
import com.devloyde.tedstat.models.*
import com.devloyde.tedstat.networking.NetworkServiceBuilder
import com.devloyde.tedstat.networking.NewsEndpoints
import com.devloyde.tedstat.ui.news.NewsFragment.Companion.COUNTRY_NEWS
import com.devloyde.tedstat.ui.news.NewsFragment.Companion.GLOBAL_NEWS
import com.devloyde.tedstat.ui.news.NewsFragment.Companion.LOCAL_NEWS
import com.devloyde.tedstat.ui.news.NewsFragment.Companion.RECOMMENDED_NEWS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

class NewsRespository(
    val newsDao: NewsDao,
    val newsExecutors: ExecutorService
): NewsRespositoryInterface {

    // Retrofit request builder service to all news endpoints
    private val request = NetworkServiceBuilder.buildService(NewsEndpoints::class.java)

    // Database Timeout id for all news
    private val recommendedNewsTimeout: Int = 0
    private val localNewsTimeout: Int = 1
    private val globalNewsTimeout: Int = 2
    private val countryNewsTimeout: Int = 3

    override fun getRecommendedNews(){
        newsExecutors.execute {
            Log.d("RECOMMENDED NEWS", "checking db for recommended news")
            val timeout = newsDao.checkTimeout(recommendedNewsTimeout)
            val expireTime = TimeUnit.HOURS.toMillis(1)
            if (timeout != null) {
                if (timeout.timeout < System.currentTimeMillis() - expireTime) {
                    Log.d("RECOMMENDED NEWS", "Not available in Db recommended news")
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
                                if (!response.body()!!.error) {
                                    newsExecutors.execute {
                                        Log.d("RECOMMENDED NEWS", "Success fetching recommended news")
                                        newsDao.deleteTimeout(recommendedNewsTimeout)
                                        newsDao.deleteRecommendedNews()
                                        Log.d("RECOMMENDED NEWS", "Delete old recommended news timeout")

                                        Log.d(
                                            "RECOMMENDED NEWS",
                                            "Success saving recommended news to db"
                                        )
                                        newsDao.saveRecommendedNews(*response.body()!!.data.toTypedArray())
                                        Log.d("RECOMMENDED NEWS", "Saving recommended news timeout")
                                        newsDao.saveTimeout(
                                            TimeoutCheck(
                                                id = recommendedNewsTimeout,
                                                name = RECOMMENDED_NEWS
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    })
                }
            }else{
                Log.d("RECOMMENDED NEWS", "Timeout null so make request")
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
                            if (!response.body()!!.error) {
                                newsExecutors.execute {
                                    Log.d("RECOMMENDED NEWS", "Success fetching recommended news")
                                    newsDao.deleteTimeout(recommendedNewsTimeout)
                                    newsDao.deleteRecommendedNews()
                                    Log.d("RECOMMENDED NEWS", "Delete old recommended news timeout")

                                    Log.d(
                                        "RECOMMENDED NEWS",
                                        "Success saving recommended news to db"
                                    )
                                    newsDao.saveRecommendedNews(*response.body()!!.data.toTypedArray())
                                    Log.d("RECOMMENDED NEWS", "Saving recommended news timeout")
                                    newsDao.saveTimeout(
                                        TimeoutCheck(
                                            id = recommendedNewsTimeout,
                                            name = RECOMMENDED_NEWS
                                        )
                                    )
                                }
                            }
                        }
                    }
                })
            }

        }
    }

    override fun getLocalNews() {
        newsExecutors.execute {
            val timeout = newsDao.checkTimeout(localNewsTimeout)
            val expireTime = TimeUnit.HOURS.toMillis(1)
            if (timeout != null) {
                if (timeout.timeout < System.currentTimeMillis() - expireTime) {
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
                                if (!response.body()!!.error) {
                                    newsExecutors.execute {
                                        Log.d("LOCAL NEWS", "Success fetching local news")
                                        newsDao.deleteTimeout(localNewsTimeout)
                                        newsDao.deleteLocalNews()

                                        newsDao.saveLocalNews(*response.body()!!.data.toTypedArray())
                                        newsDao.saveTimeout(
                                            TimeoutCheck(
                                                id = localNewsTimeout,
                                                name = LOCAL_NEWS
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    })
                }
            }else{
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
                            if (!response.body()!!.error) {
                                newsExecutors.execute {
                                    Log.d("LOCAL NEWS", "Success fetching local news")
                                    newsDao.deleteTimeout(localNewsTimeout)
                                    newsDao.deleteLocalNews()

                                    newsDao.saveLocalNews(*response.body()!!.data.toTypedArray())
                                    newsDao.saveTimeout(
                                        TimeoutCheck(
                                            id = localNewsTimeout,
                                            name = LOCAL_NEWS
                                        )
                                    )
                                }
                            }
                        }
                    }
                })
            }
        }
    }

    override fun getGlobalNews() {
        newsExecutors.execute {
            val timeout = newsDao.checkTimeout(globalNewsTimeout)
            val expireTime = TimeUnit.HOURS.toMillis(1)
            if (timeout != null) {
                if (timeout.timeout < System.currentTimeMillis() - expireTime) {
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
                                if (!response.body()!!.error) {
                                    newsExecutors.execute {
                                        Log.d("GLOBAL NEWS", "Success fetching global news")
                                        newsDao.deleteTimeout(globalNewsTimeout)
                                        newsDao.deleteGlobalNews()
                                        newsDao.saveGlobalNews(*response.body()!!.data.toTypedArray())
                                        newsDao.saveTimeout(
                                            TimeoutCheck(id = globalNewsTimeout, name = GLOBAL_NEWS)
                                        )
                                    }
                                }

                            }
                        }

                    })
                }
            }else{
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
                            if (!response.body()!!.error) {
                                newsExecutors.execute {
                                    Log.d("GLOBAL NEWS", "Success fetching global news")
                                    newsDao.deleteTimeout(globalNewsTimeout)
                                    newsDao.deleteGlobalNews()
                                    newsDao.saveGlobalNews(*response.body()!!.data.toTypedArray())
                                    newsDao.saveTimeout(
                                        TimeoutCheck(id = globalNewsTimeout, name = GLOBAL_NEWS)
                                    )
                                }
                            }

                        }
                    }

                })
            }
        }
    }

    override fun getCountryNews(countryIso: String) {
        newsExecutors.execute {
            val timeout = newsDao.checkTimeout(countryNewsTimeout)
            val expireTime = TimeUnit.HOURS.toMillis(1)
            if (timeout != null) {
                if (timeout.timeout < System.currentTimeMillis() - expireTime) {
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
                                if (!response.body()!!.error) {
                                    newsExecutors.execute {
                                        newsDao.deleteTimeout(countryNewsTimeout)
                                        newsDao.deleteCountryNews()

                                        newsDao.saveCountryNews(*response.body()!!.data.toTypedArray())
                                        newsDao.saveTimeout(
                                            TimeoutCheck(
                                                id = countryNewsTimeout,
                                                name = COUNTRY_NEWS
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    })
                }
            }else{
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
                            if (!response.body()!!.error) {
                                newsExecutors.execute {
                                    newsDao.deleteTimeout(countryNewsTimeout)
                                    newsDao.deleteCountryNews()

                                    newsDao.saveCountryNews(*response.body()!!.data.toTypedArray())
                                    newsDao.saveTimeout(
                                        TimeoutCheck(
                                            id = countryNewsTimeout,
                                            name = COUNTRY_NEWS
                                        )
                                    )
                                }
                            }
                        }
                    }
                })
            }
        }
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