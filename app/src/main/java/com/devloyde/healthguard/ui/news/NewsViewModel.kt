package com.devloyde.healthguard.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devloyde.healthguard.models.CountryNews
import com.devloyde.healthguard.models.GlobalNews
import com.devloyde.healthguard.models.LocalNews
import com.devloyde.healthguard.models.RecommendedNews
import com.devloyde.healthguard.respositories.NewsRepository

class NewsViewModel : ViewModel() {

    private var newsRepository: NewsRepository = NewsRepository

    var recommendedNews: LiveData<List<RecommendedNews>> = newsRepository.getRecommendedNews(1)
    var localNews: LiveData<List<LocalNews>> = newsRepository.getLocalNews()
    var countryNews: LiveData<List<Any>> = newsRepository.getCountryNews("NG")
    var globalNews: LiveData<List<GlobalNews>> = newsRepository.getGlobalNews()

}
