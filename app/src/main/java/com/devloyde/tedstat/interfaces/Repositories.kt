package com.devloyde.tedstat.interfaces


interface InfoRepositoryInterface {

    fun getAdvisoryInfo() {}

    fun getFaqInfo() {}

}

interface NewsRespositoryInterface {

    fun getRecommendedNews() {}

    fun getLocalNews() {}

    fun getGlobalNews() {}

    fun getCountryNews(countryIso: String) {}

}

interface StatRepositoryInterface {

    fun getGlobalStat() {}

    fun getCountriesStat() {}

}