package com.devloyde.tedstat.respositories

import android.util.Log
import com.devloyde.tedstat.db.StatDao
import com.devloyde.tedstat.interfaces.StatRepositoryInterface
import com.devloyde.tedstat.models.*
import com.devloyde.tedstat.networking.NetworkServiceBuilder
import com.devloyde.tedstat.networking.StatEndpoints
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

class StatRepository(val statDao: StatDao, val statExecutors: ExecutorService) :
    StatRepositoryInterface {
    // Retrofit request builder service to all news endpoints
    private val request = NetworkServiceBuilder.buildService(StatEndpoints::class.java)
    private val globalStatsTimeout: Int = 5
    private val countriesStatTimeout: Int = 6

    override fun getGlobalStat() {
        statExecutors.execute {
            Log.d(GLOBAL_STAT_TAG, "checking db for global stat")
            val timeout = statDao.checkTimeout(globalStatsTimeout)
            val expireTime = TimeUnit.HOURS.toMillis(1)
            if (timeout != null) {
                if (timeout.timeout < System.currentTimeMillis() - expireTime) {
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
                            if (response.isSuccessful) {
                                if (!response.body()!!.error) {
                                    statExecutors.execute {
                                        Log.d(GLOBAL_STAT_TAG, "Success fetching global stat")
                                        Log.d(
                                            GLOBAL_STAT_TAG,
                                            "delete timeout and previous global stat data only on success"
                                        )
                                        statDao.deleteTimeout(globalStatsTimeout)
                                        statDao.deleteGlobalStat()
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
                        }
                    })
                }
            } else {
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
                        if (response.isSuccessful) {
                            if (!response.body()!!.error) {
                                statExecutors.execute {
                                    Log.d(GLOBAL_STAT_TAG, "Success fetching global stat")
                                    Log.d(
                                        GLOBAL_STAT_TAG,
                                        "delete timeout and previous global stat data only on success"
                                    )
                                    statDao.deleteTimeout(globalStatsTimeout)
                                    statDao.deleteGlobalStat()
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
                    }
                })
            }

        }
    }

    override fun getCountriesStat() {
        statExecutors.execute {
            Log.d(COUNTRY_STAT_TAG, "checking db for countries stat list")
            val timeout = statDao.checkTimeout(countriesStatTimeout)
            val expireTime = TimeUnit.HOURS.toMillis(1)
            if (timeout != null) {
                if (timeout.timeout < System.currentTimeMillis() - expireTime) {
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
                            if (response.isSuccessful) {
                                if (!response.body()!!.error) {
                                    statExecutors.execute {
                                        Log.d(COUNTRY_STAT_TAG, "Success fetching countries stat")
                                        Log.d(
                                            COUNTRY_STAT_TAG,
                                            "delete timeout and previous global stat data only on success"
                                        )
                                        statDao.deleteTimeout(countriesStatTimeout)
                                        statDao.deleteCountriesStat()
                                        Log.d(
                                            COUNTRY_STAT_TAG,
                                            "Success saving countries stats to db"
                                        )
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
                        }
                    })
                }
            } else {
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
                        if (response.isSuccessful) {
                            if (!response.body()!!.error) {
                                statExecutors.execute {
                                    Log.d(COUNTRY_STAT_TAG, "Success fetching countries stat")
                                    Log.d(
                                        COUNTRY_STAT_TAG,
                                        "delete timeout and previous global stat data only on success"
                                    )
                                    statDao.deleteTimeout(countriesStatTimeout)
                                    statDao.deleteCountriesStat()
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
                    }
                })
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        const val GLOBAL_STAT_TAG = "GLOBAL STAT"
        const val COUNTRY_STAT_TAG = "COUNTRY STAT"

        @Volatile
        private var STAT_RESPOSITORY_INSTANCE: StatRepository? = null

        fun getStatRepository(
            statDao: StatDao,
            executor: ExecutorService
        ): StatRepository {
            val tempInstance = STAT_RESPOSITORY_INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = StatRepository(statDao, executor)
                STAT_RESPOSITORY_INSTANCE = instance
                return instance
            }
        }
    }
}