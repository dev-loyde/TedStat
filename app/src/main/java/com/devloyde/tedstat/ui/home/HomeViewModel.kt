package com.devloyde.tedstat.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.devloyde.tedstat.db.HealthGuardDatabase
import com.devloyde.tedstat.db.InfoDao
import com.devloyde.tedstat.models.*
import com.devloyde.tedstat.respositories.InfoRepository
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