package com.devloyde.healthguard.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devloyde.healthguard.db.HealthGuardDatabase
import com.devloyde.healthguard.db.NewsDao
import com.devloyde.healthguard.models.CountryNews
import com.devloyde.healthguard.models.GlobalNews
import com.devloyde.healthguard.models.LocalNews
import com.devloyde.healthguard.models.RecommendedNews
import com.devloyde.healthguard.respositories.NewsRespository
import java.util.concurrent.Executors

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private var newsRepository: NewsRespository
    private val newsExecutor = Executors.newFixedThreadPool(4)
    private val newsDao: NewsDao = HealthGuardDatabase.getDatabase(application).newsDao()

    init {
        newsRepository = NewsRespository.getNewsRepository(newsDao, newsExecutor)
        fetchNews()
    }

    var recommendedNews: LiveData<List<RecommendedNews>> = newsDao.loadRecommendedNews()
    var localNews: LiveData<List<LocalNews>> = newsDao.loadLocalNews()
    var countryNews: LiveData<List<CountryNews>> = newsDao.loadCountryNews()
    var globalNews: LiveData<List<GlobalNews>> = newsDao.loadGlobalNews()

    fun refresh() {
        fetchNews()
    }

    private fun fetchNews(){
        newsRepository.getRecommendedNews()
        newsRepository.getLocalNews()
        newsRepository.getCountryNews("NG")
        newsRepository.getGlobalNews()
    }

}
