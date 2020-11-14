package com.devloyde.tedstat.ui.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devloyde.tedstat.db.HealthGuardDatabase
import com.devloyde.tedstat.db.SharedPref
import com.devloyde.tedstat.db.StatDao
import com.devloyde.tedstat.models.GlobalStat
import com.devloyde.tedstat.models.StatCountries
import com.devloyde.tedstat.respositories.StatRepository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val statRespository: StatRepository
    private val executor: ExecutorService = Executors.newFixedThreadPool(2)
    private val statDao: StatDao = HealthGuardDatabase.getDatabase(application).statDao()
    private var currentCountry: MutableLiveData<StatCountries> = MutableLiveData()
    var countriesStat: LiveData<List<StatCountries>> = MutableLiveData()
    var topAffectedCountriesStat: LiveData<List<StatCountries>> = MutableLiveData()
    val sharedPreference = SharedPref(application)

    init {
        Log.d("dash-view-model", "view model initialized")
        statRespository = StatRepository.getStatRepository(statDao, executor)
        fetchStat()
        countriesStat = statDao.loadCountriesStat()
        topAffectedCountriesStat = statDao.loadTopAffectedCountriesStat()
    }

    var globalStat: LiveData<GlobalStat> = statDao.loadGlobalStat()

    private fun fetchStat() {
        statRespository.getCountriesStat()
        statRespository.getGlobalStat()
    }

    fun refresh() {
        fetchStat()
    }

    fun setCurrentCountry(country: StatCountries) {
        currentCountry.value = country
        sharedPreference.saveDashboardSelectedCountry(country.country!!)
    }

    fun getCurrentCountry(): LiveData<StatCountries> {
        return currentCountry
    }

    fun getDefaultCountry(): LiveData<StatCountries> {
        return statDao.loadOneCountriesStat(sharedPreference.loadDashboardSelectedCountry()!!)
    }

}