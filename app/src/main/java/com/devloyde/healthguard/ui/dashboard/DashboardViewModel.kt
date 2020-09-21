package com.devloyde.healthguard.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devloyde.healthguard.db.HealthGuardDatabase
import com.devloyde.healthguard.db.StatDao
import com.devloyde.healthguard.models.GlobalStat
import com.devloyde.healthguard.models.StatCountries
import com.devloyde.healthguard.respositories.StatRepository
import java.util.concurrent.Executors

class DashboardViewModel(application: Application) : AndroidViewModel(application){
    private val statRespository: StatRepository
    private val executor = Executors.newFixedThreadPool(2)
    private val statDao : StatDao = HealthGuardDatabase.getDatabase(application).statDao()

    init{
        statRespository = StatRepository.getStatRepository(statDao,executor)
    }

    var globalStat: LiveData<List<GlobalStat>> = statRespository.getGlobalStat()
    var countriesStat: LiveData<List<StatCountries>> = statRespository.getCountriesStat()

    fun getCountry(name: String): LiveData<StatCountries> {
        return statDao.loadOneCountriesStat(name)
    }

}