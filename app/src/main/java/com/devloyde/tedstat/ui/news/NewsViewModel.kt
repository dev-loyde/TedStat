package com.devloyde.tedstat.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.devloyde.tedstat.db.HealthGuardDatabase
import com.devloyde.tedstat.db.NewsDao
import com.devloyde.tedstat.models.CountryNews
import com.devloyde.tedstat.models.GlobalNews
import com.devloyde.tedstat.models.LocalNews
import com.devloyde.tedstat.models.RecommendedNews
import com.devloyde.tedstat.respositories.NewsRespository
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
