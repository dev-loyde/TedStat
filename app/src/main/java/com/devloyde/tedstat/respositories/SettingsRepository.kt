package com.devloyde.tedstat.respositories

import android.util.Log
import com.devloyde.tedstat.models.FeedBack
import com.devloyde.tedstat.networking.FeedBackEndpoints
import com.devloyde.tedstat.networking.NetworkServiceBuilder
import com.hadilq.liveevent.LiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService

class SettingsRepository(
    private val executors: ExecutorService
) {
    // Retrofit request builder service to all news endpoints
    private val request =
        NetworkServiceBuilder.buildFeedBackService(FeedBackEndpoints::class.java)

    val success: LiveEvent<Boolean> = LiveEvent()
    val tag = "FEEDBACK_REPOSITORY - "

    fun submitFeedback(fields: FeedBack): LiveEvent<Boolean> {
        executors.execute {
            val call = request.submitFeedBack(
                fields.name,
                fields.email,
                fields.message,
                fields.date
            )

            call.enqueue(object : Callback<Void> {

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d(tag,"Error submitting feedback ${t.message} ${t.stackTrace}")
                    success.postValue(false)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d(tag,"Success submitting feedback")
                        success.postValue(true)
                    }
                }

            })
        }
        return success
    }

    companion object {
        // Singleton prevents multiple instances running at the
        // same time.
        @Volatile
        private var SETTINGS_REPOSITORY_INSTANCE: SettingsRepository? = null

        fun getSettingsRepository(
            executor: ExecutorService
        ): SettingsRepository {
            val tempInstance = SETTINGS_REPOSITORY_INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = SettingsRepository(executor)
                SETTINGS_REPOSITORY_INSTANCE = instance
                return instance
            }
        }
    }
}