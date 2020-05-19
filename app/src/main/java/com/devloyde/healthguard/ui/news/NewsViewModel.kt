package com.devloyde.healthguard.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devloyde.healthguard.db.HealthGuardDatabase
import com.devloyde.healthguard.models.CountryNews
import com.devloyde.healthguard.models.GlobalNews
import com.devloyde.healthguard.models.LocalNews
import com.devloyde.healthguard.models.RecommendedNews
import com.devloyde.healthguard.respositories.NewsRespository
import java.util.concurrent.Executors

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val newsRepository: NewsRespository
    private val newsExecutor = Executors.newFixedThreadPool(4)

    init{
        val newsDao = HealthGuardDatabase.getDatabase(application).newsDao()
        newsRepository = NewsRespository.getNewsRepository(newsDao,newsExecutor)
    }

    var recommendedNews: LiveData<List<RecommendedNews>> = newsRepository.getRecommendedNews()
    var localNews: LiveData<List<LocalNews>> = newsRepository.getLocalNews()
    var countryNews: LiveData<List<CountryNews>> = newsRepository.getCountryNews("NG")
    var globalNews: LiveData<List<GlobalNews>> = newsRepository.getGlobalNews()

}
