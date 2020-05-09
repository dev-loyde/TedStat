package com.devloyde.healthguard.respositories

import androidx.lifecycle.MutableLiveData
import com.devloyde.healthguard.models.*
import com.devloyde.healthguard.networking.NetworkServiceBuilder
import com.devloyde.healthguard.networking.NewsEndpoints
import com.devloyde.healthguard.networking.StatEndpoints
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object StatRepository {
    private val statExecutors: ExecutorService? = Executors.newFixedThreadPool(4)

    fun getGlobalStat(): MutableLiveData<GlobalStat> {

        lateinit var globalStat: MutableLiveData<GlobalStat>
        statExecutors!!.execute {
            val request = NetworkServiceBuilder.buildService(StatEndpoints::class.java)
            val call = request.getGlobalStat()

            call.enqueue(object : Callback<GlobalStat> {
                override fun onFailure(call: Call<GlobalStat>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(
                    call: Call<GlobalStat>,
                    response: Response<GlobalStat>
                ) {
                    if (response.isSuccessful) globalStat.value = response.body() as GlobalStat
                }
            })

        }
        return globalStat

    }

    fun getCountriesStat(): MutableLiveData<List<StatCountries>> {

        lateinit var countriesStat: MutableLiveData<List<StatCountries>>
        statExecutors!!.execute {
            val request = NetworkServiceBuilder.buildService(StatEndpoints::class.java)
            val call = request.getCountryStat()

            call.enqueue(object : Callback<List<StatCountries>> {
                override fun onFailure(call: Call<List<StatCountries>>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(
                    call: Call<List<StatCountries>>,
                    response: Response<List<StatCountries>>
                ) {
                    if (response.isSuccessful) countriesStat.value = response.body() as List<StatCountries>
                }
            })

        }
        return countriesStat

    }

    fun getStatHistory(): MutableLiveData<List<StatHistory>> {

        lateinit var globalHistory: MutableLiveData<List<StatHistory>>
        statExecutors!!.execute {
            val request = NetworkServiceBuilder.buildService(StatEndpoints::class.java)
            val call = request.getHistoryStat()

            call.enqueue(object : Callback<List<StatHistory>> {
                override fun onFailure(call: Call<List<StatHistory>>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(
                    call: Call<List<StatHistory>>,
                    response: Response<List<StatHistory>>
                ) {
                    if (response.isSuccessful) globalHistory.value = response.body() as List<StatHistory>
                }
            })

        }
        return globalHistory

    }

}