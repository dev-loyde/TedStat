package com.devloyde.tedstat.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.devloyde.tedstat.models.*
import com.devloyde.tedstat.respositories.SettingsRepository
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
