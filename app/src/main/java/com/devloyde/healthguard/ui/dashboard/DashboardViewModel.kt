package com.devloyde.healthguard.ui.dashboard

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devloyde.healthguard.db.HealthGuardDatabase
import com.devloyde.healthguard.db.StatDao
import com.devloyde.healthguard.models.GlobalStat
import com.devloyde.healthguard.models.StatCountries
import com.devloyde.healthguard.respositories.StatRepository
import java.util.concurrent.Executors

class DashboardViewModel(application: Application) : AndroidViewModel(application){
    private val statRespository: StatRepository
    val executor = Executors.newFixedThreadPool(2)
    val statDao : StatDao = HealthGuardDatabase.getDatabase(application).statDao()
    private var currentCountry :MutableLiveData<StatCountries> = MutableLiveData()
    var countriesStat: LiveData<List<StatCountries>> = MutableLiveData()

    init{
        Log.d("dash-viewmodel","view model initialized")
        statRespository = StatRepository.getStatRepository(statDao,executor)
        countriesStat = statRespository.getCountriesStat()

    }

    var globalStat: LiveData<List<GlobalStat>> = statRespository.getGlobalStat()

    fun setCurrentCountry(country: StatCountries) {
        Toast.makeText(getApplication(),"set "+country.country, Toast.LENGTH_SHORT).show()
        currentCountry.value = country
    }

    fun getCurrentCountry(): LiveData<StatCountries> {
        Toast.makeText(getApplication(),"triggered get ", Toast.LENGTH_SHORT).show()
        return currentCountry
    }

    fun getDefaultCountry(): LiveData<StatCountries> {
        return statDao.loadOneCountriesStat("Nigeria")
    }
}