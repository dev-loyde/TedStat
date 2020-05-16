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

    private val newsRepository: NewsRepository = NewsRepository

    var recommendedNews : LiveData<List<RecommendedNews>> = newsRepository.recommendedNewsRepo
    var localNews: LiveData<List<LocalNews>> = newsRepository.localNewsRepo
    var countryNews: LiveData<List<Any>> = newsRepository.countryNewsRepo
    var globalNews: LiveData<List<GlobalNews>> = newsRepository.globalNewsRepo

}
