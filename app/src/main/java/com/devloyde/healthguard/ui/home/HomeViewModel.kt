package com.devloyde.healthguard.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devloyde.healthguard.db.HealthGuardDatabase
import com.devloyde.healthguard.db.InfoDao
import com.devloyde.healthguard.models.*
import com.devloyde.healthguard.respositories.InfoRepository
import com.devloyde.healthguard.respositories.NewsRespository
import com.devloyde.healthguard.respositories.StatRepository
import java.util.concurrent.Executors

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var infoRepository: InfoRepository
    private val executor = Executors.newFixedThreadPool(2)
    var advisoryInfo: LiveData<List<AdvisoryInfo>>
    var faqInfo: LiveData<List<FaqInfo>>
    private val infoDao: InfoDao = HealthGuardDatabase.getDatabase(application).infoDao()

    init {
        infoRepository = InfoRepository.getStatRepository(infoDao, executor)
        fetchInfo()
        advisoryInfo = infoDao.loadAdvisory()
        faqInfo = infoDao.loadFaq()
    }

    fun refresh() {
        fetchInfo()
    }

    private fun fetchInfo() {
        infoRepository.getAdvisoryInfo()
        infoRepository.getFaqInfo()
    }

}