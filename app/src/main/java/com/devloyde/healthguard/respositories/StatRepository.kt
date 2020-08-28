package com.devloyde.healthguard.respositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devloyde.healthguard.db.NewsDao
import com.devloyde.healthguard.db.StatDao
import com.devloyde.healthguard.models.*
import com.devloyde.healthguard.networking.NetworkServiceBuilder
import com.devloyde.healthguard.networking.NewsEndpoints
import com.devloyde.healthguard.networking.StatEndpoints
import com.devloyde.healthguard.ui.news.NewsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StatRepository(val statDao: StatDao, val statExecutors: ExecutorService) {
    // Retrofit request builder service to all news endpoints
    private val request = NetworkServiceBuilder.buildService(StatEndpoints::class.java)
    private val globalStatsTimeout: Int = 5
    private val countriesStatTimeout: Int = 6
    private val historyStaTimeout: Int = 7

    companion object {
        const val GLOBAL_STAT_TAG = "GLOBAL STAT"
        const val COUNTRY_STAT_TAG = "COUNTRY STAT"
        const val HISTORY_STAT_TAG = "HISTORY STAT"
    }

    fun getGlobalStat(): LiveData<GlobalStat> {
        statExecutors.execute {
            Log.d(GLOBAL_STAT_TAG, "checking db for global stat")
            val timeout = statDao.checkTimeout(globalStatsTimeout)
            if (timeout == null || timeout.timeout < System.currentTimeMillis()) {
                Log.d(GLOBAL_STAT_TAG, "user eligible to fetch new stat from server")
                val call = request.getGlobalStat()

                call.enqueue(object : Callback<GlobalStatResponse> {
                    override fun onFailure(call: Call<GlobalStatResponse>, t: Throwable) {
                        Log.d(GLOBAL_STAT_TAG, "Error fetching global stat")
                    }

                    override fun onResponse(
                        call: Call<GlobalStatResponse>,
                        response: Response<GlobalStatResponse>
                    ) {
                        if (response.isSuccessful && !response.body()?.error!!) {
                            Log.d(GLOBAL_STAT_TAG, "Success fetching global stat")

                            Log.d(
                                GLOBAL_STAT_TAG,
                                "delete timeout and previous global stat data only on success"
                            )
                            statDao.deleteTimeout(globalStatsTimeout)
                            statDao.deleteGlobalStat()
                            statExecutors.execute {
                                Log.d(GLOBAL_STAT_TAG, "Success saving global stats to db")
                                statDao.saveGlobalStat(response.body()!!.data)
                                Log.d(GLOBAL_STAT_TAG, "Saving recommended news timeout")
                                statDao.saveTimeout(
                                    TimeoutCheck(
                                        id = globalStatsTimeout,
                                        name = "GLOBAL_STAT"
                                    )
                                )
                            }
                        }
                    }
                })
            }

        }
        return statDao.loadGlobalStat()
    }

    fun getCountriesStat(): LiveData<List<StatCountries>> {
        statExecutors.execute {
            Log.d(COUNTRY_STAT_TAG, "checking db for countries stat list")
            val timeout = statDao.checkTimeout(countriesStatTimeout)
            if (timeout == null || timeout.timeout < System.currentTimeMillis()) {
                Log.d(COUNTRY_STAT_TAG, "user eligible to fetch new stat from server")
                val call = request.getCountryStat()

                call.enqueue(object : Callback<CountryStatResponse> {
                    override fun onFailure(call: Call<CountryStatResponse>, t: Throwable) {
                        Log.d(COUNTRY_STAT_TAG, "Error fetching countries stat list")
                    }

                    override fun onResponse(
                        call: Call<CountryStatResponse>,
                        response: Response<CountryStatResponse>
                    ) {
                        if (response.isSuccessful && !response.body()?.error!!) {
                            Log.d(COUNTRY_STAT_TAG, "Success fetching countries stat")
                            Log.d(
                                COUNTRY_STAT_TAG,
                                "delete timeout and previous global stat data only on success"
                            )
                            statDao.deleteTimeout(countriesStatTimeout)
                            statDao.deleteCountriesStat()
                            statExecutors.execute {
                                Log.d(COUNTRY_STAT_TAG, "Success saving countries stats to db")
                                statDao.saveStatCountries(*response.body()!!.data.toTypedArray())
                                Log.d(COUNTRY_STAT_TAG, "Saving countries stat timeout")
                                statDao.saveTimeout(
                                    TimeoutCheck(
                                        id = countriesStatTimeout,
                                        name = "COUNTRIES_STAT"
                                    )
                                )
                            }
                        }
                    }
                })
            }

        }
        return statDao.loadCountriesStat()
    }

    fun getHistoryStat(): LiveData<List<StatHistory>> {
        statExecutors.execute {
            Log.d(HISTORY_STAT_TAG, "checking db for history stat")
            val timeout = statDao.checkTimeout(historyStaTimeout)
            if (timeout == null || timeout.timeout < System.currentTimeMillis()) {
                Log.d(HISTORY_STAT_TAG, "user eligible to fetch new stat from server")
                val call = request.getHistoryStat()

                call.enqueue(object : Callback<HistoryStatResponse> {
                    override fun onFailure(call: Call<HistoryStatResponse>, t: Throwable) {
                        Log.d(HISTORY_STAT_TAG, "Error fetching history statistics")
                    }

                    override fun onResponse(
                        call: Call<HistoryStatResponse>,
                        response: Response<HistoryStatResponse>
                    ) {
                        if (response.isSuccessful && !response.body()?.error!!) {
                            Log.d(HISTORY_STAT_TAG, "Success fetching history stat")

                            Log.d(
                                HISTORY_STAT_TAG,
                                "delete timeout and previous history stat data only on success"
                            )
                            statDao.deleteTimeout(historyStaTimeout)
                            statDao.deleteHistoryStat()
                            statExecutors.execute {
                                Log.d(HISTORY_STAT_TAG, "Success saving history stats to db")
                                statDao.saveStatHistory(*response.body()!!.data.toTypedArray())
                                Log.d(HISTORY_STAT_TAG, "Saving history news timeout")
                                statDao.saveTimeout(
                                    TimeoutCheck(
                                        id = historyStaTimeout,
                                        name = "HISTORY_STAT"
                                    )
                                )
                            }
                        }
                    }
                })
            }

        }
        return statDao.loadHistoryStat()
    }

}