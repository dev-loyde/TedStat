package com.devloyde.healthguard.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devloyde.healthguard.db.HealthGuardDatabase
import com.devloyde.healthguard.models.*
import com.devloyde.healthguard.respositories.NewsRespository
import com.devloyde.healthguard.respositories.SettingsRepository
import com.hadilq.liveevent.LiveEvent
import java.util.concurrent.Executors

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepository: SettingsRepository
    private val executor = Executors.newFixedThreadPool(1)

    init{
        settingsRepository = SettingsRepository.getSettingsRepository(executor)
    }

    fun submitFeedBack(feedback: FeedBack): LiveEvent<Boolean> {
        return settingsRepository.submitFeedback(feedback)
    }
}
