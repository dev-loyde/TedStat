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

    private val statRespository: StatRepository
    private val infoRepository: InfoRepository
    private val executor = Executors.newFixedThreadPool(4)

    init{
        val statDao = HealthGuardDatabase.getDatabase(application).statDao()
        val infoDao = HealthGuardDatabase.getDatabase(application).infoDao()
        statRespository = StatRepository.getStatRepository(statDao,executor)
        infoRepository = InfoRepository.getStatRepository(infoDao,executor)
    }

    var globalStat: LiveData<List<GlobalStat>> = statRespository.getGlobalStat()
    var countriesStat: LiveData<List<StatCountries>> = statRespository.getCountriesStat()
    var advisoryInfo: LiveData<List<AdvisoryInfo>> = infoRepository.getAdvisoryInfo()
    var faqInfo: LiveData<List<FaqInfo>> = infoRepository.getFaqInfo()

}