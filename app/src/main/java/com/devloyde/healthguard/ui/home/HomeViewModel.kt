package com.devloyde.healthguard.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devloyde.healthguard.db.HealthGuardDatabase
import com.devloyde.healthguard.models.*
import com.devloyde.healthguard.respositories.InfoRepository
import com.devloyde.healthguard.respositories.NewsRespository
import com.devloyde.healthguard.respositories.StatRepository
import java.util.concurrent.Executors

class HomeViewModel(application: Application) : AndroidViewModel(application){
    private val infoRepository: InfoRepository
    private val executor = Executors.newFixedThreadPool(2)
    val advisoryInfo: LiveData<List<AdvisoryInfo>>
    val faqInfo: LiveData<List<FaqInfo>>

    init{
        val infoDao = HealthGuardDatabase.getDatabase(application).infoDao()
        infoRepository = InfoRepository.getStatRepository(infoDao,executor)
        advisoryInfo = infoRepository.getAdvisoryInfo()
        faqInfo = infoRepository.getFaqInfo()
    }

}