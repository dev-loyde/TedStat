package com.devloyde.tedstat.respositories

import android.util.Log
import com.devloyde.tedstat.db.InfoDao
import com.devloyde.tedstat.interfaces.InfoRepositoryInterface
import com.devloyde.tedstat.models.*
import com.devloyde.tedstat.networking.InfoEndpoints
import com.devloyde.tedstat.networking.NetworkServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

class InfoRepository(val infoDao: InfoDao, val infoExecutors: ExecutorService) : InfoRepositoryInterface {
    // Retrofit request builder service to all news endpoints
    private val request = NetworkServiceBuilder.buildService(InfoEndpoints::class.java)
    private val advisoryTimeout: Int = 10
    private val faqTimeout: Int = 11

    override fun getAdvisoryInfo() {
        infoExecutors.execute {
            Log.d(ADVISORY_INFO_TAG, "checking db for advisory info")
            val timeout = infoDao.checkTimeout(advisoryTimeout)
            val expireTime = TimeUnit.MINUTES.toMillis(1)
            if (timeout != null) {
                if (timeout.timeout < System.currentTimeMillis() - expireTime) {
                    Log.d(ADVISORY_INFO_TAG, "user eligible to fetch advisory info from server")
                    val call = request.getAdvisoryInfo()
                    call.enqueue(object : Callback<AdvisoryResponse> {
                        override fun onFailure(call: Call<AdvisoryResponse>, t: Throwable) {
                            Log.d(ADVISORY_INFO_TAG, "Error fetching advisory info")
                        }

                        override fun onResponse(
                            call: Call<AdvisoryResponse>,
                            response: Response<AdvisoryResponse>
                        ) {
                            if (response.isSuccessful && !response.body()?.error!!) {
                                if (!response.body()!!.error) {
                                    infoExecutors.execute {
                                        Log.d(ADVISORY_INFO_TAG, "Success fetching advisory info")
                                        Log.d(
                                            ADVISORY_INFO_TAG,
                                            "delete timeout and previous advisory info data only on success"
                                        )
                                        infoDao.deleteTimeout(advisoryTimeout)
                                        infoDao.deleteAdvisory()
                                        Log.d(
                                            ADVISORY_INFO_TAG,
                                            "Success saving advisory info to db"
                                        )
                                        infoDao.saveAdvisory(*response.body()!!.data.toTypedArray())
                                        Log.d(
                                            ADVISORY_INFO_TAG,
                                            "Saving advisory info news timeout"
                                        )
                                        infoDao.saveTimeout(
                                            TimeoutCheck(
                                                id = advisoryTimeout,
                                                name = "ADVISORY INFO"
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    })
                }
            }else{
                Log.d(ADVISORY_INFO_TAG, "user eligible to fetch advisory info from server")
                val call = request.getAdvisoryInfo()
                call.enqueue(object : Callback<AdvisoryResponse> {
                    override fun onFailure(call: Call<AdvisoryResponse>, t: Throwable) {
                        Log.d(ADVISORY_INFO_TAG, "Error fetching advisory info")
                    }

                    override fun onResponse(
                        call: Call<AdvisoryResponse>,
                        response: Response<AdvisoryResponse>
                    ) {
                        if (response.isSuccessful && !response.body()?.error!!) {
                            if (!response.body()!!.error) {
                                infoExecutors.execute {
                                    Log.d(ADVISORY_INFO_TAG, "Success fetching advisory info")
                                    Log.d(
                                        ADVISORY_INFO_TAG,
                                        "delete timeout and previous advisory info data only on success"
                                    )
                                    infoDao.deleteTimeout(advisoryTimeout)
                                    infoDao.deleteAdvisory()
                                    Log.d(
                                        ADVISORY_INFO_TAG,
                                        "Success saving advisory info to db"
                                    )
                                    infoDao.saveAdvisory(*response.body()!!.data.toTypedArray())
                                    Log.d(
                                        ADVISORY_INFO_TAG,
                                        "Saving advisory info news timeout"
                                    )
                                    infoDao.saveTimeout(
                                        TimeoutCheck(
                                            id = advisoryTimeout,
                                            name = "ADVISORY INFO"
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

    override fun getFaqInfo() {
        infoExecutors.execute {
            Log.d(FAQ_INFO_TAG, "checking db for faq info")
            val timeout = infoDao.checkTimeout(faqTimeout)
            val expireTime = TimeUnit.HOURS.toMillis(1)
                if (timeout != null) {
                    if (timeout.timeout < System.currentTimeMillis() - expireTime) {
                        Log.d(FAQ_INFO_TAG, "user eligible to fetch faq info from server")
                        val call = request.getFaqInfo()
                        call.enqueue(object : Callback<FaqResponse> {
                            override fun onFailure(call: Call<FaqResponse>, t: Throwable) {
                                Log.d(FAQ_INFO_TAG, "Error fetching faq info")
                            }

                            override fun onResponse(
                                call: Call<FaqResponse>,
                                response: Response<FaqResponse>
                            ) {
                                if (response.isSuccessful && !response.body()?.error!!) {
                                    if (!response.body()!!.error) {
                                        infoExecutors.execute {
                                            Log.d(FAQ_INFO_TAG, "Success fetching faq info")
                                            Log.d(
                                                FAQ_INFO_TAG,
                                                "delete timeout and previous faq info data only on success"
                                            )
                                            infoDao.deleteTimeout(faqTimeout)
                                            infoDao.deleteFaq()
                                            Log.d(FAQ_INFO_TAG, "Success saving faq info to db")
                                            infoDao.saveFaq(*response.body()!!.data.toTypedArray())
                                            Log.d(FAQ_INFO_TAG, "Saving faq info news timeout")
                                            infoDao.saveTimeout(
                                                TimeoutCheck(
                                                    id = faqTimeout,
                                                    name = "FAQ INFO"
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        })
                    }
                }else{
                    Log.d(FAQ_INFO_TAG, "user eligible to fetch faq info from server")
                    val call = request.getFaqInfo()
                    call.enqueue(object : Callback<FaqResponse> {
                        override fun onFailure(call: Call<FaqResponse>, t: Throwable) {
                            Log.d(FAQ_INFO_TAG, "Error fetching faq info")
                        }

                        override fun onResponse(
                            call: Call<FaqResponse>,
                            response: Response<FaqResponse>
                        ) {
                            if (response.isSuccessful && !response.body()?.error!!) {
                                if (!response.body()!!.error) {
                                    infoExecutors.execute {
                                        Log.d(FAQ_INFO_TAG, "Success fetching faq info")
                                        Log.d(
                                            FAQ_INFO_TAG,
                                            "delete timeout and previous faq info data only on success"
                                        )
                                        infoDao.deleteTimeout(faqTimeout)
                                        infoDao.deleteFaq()
                                        Log.d(FAQ_INFO_TAG, "Success saving faq info to db")
                                        infoDao.saveFaq(*response.body()!!.data.toTypedArray())
                                        Log.d(FAQ_INFO_TAG, "Saving faq info news timeout")
                                        infoDao.saveTimeout(
                                            TimeoutCheck(
                                                id = faqTimeout,
                                                name = "FAQ INFO"
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
        const val ADVISORY_INFO_TAG = "ADVISORY INFO"
        const val FAQ_INFO_TAG = "FAQ INFO"

        @Volatile
        private var INFO_RESPOSITORY_INSTANCE: InfoRepository? = null

        fun getStatRepository(
            infoDao: InfoDao,
            executor: ExecutorService
        ): InfoRepository {
            val tempInstance = INFO_RESPOSITORY_INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = InfoRepository(infoDao, executor)
                INFO_RESPOSITORY_INSTANCE = instance
                return instance
            }
        }
    }
}