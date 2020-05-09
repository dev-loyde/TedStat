package com.devloyde.healthguard.models

data class NewsCard(
    val image: String?,
    val title: String?,
    val link: String?,
    val date: String?,
    val provider: String?
)

data class RecommendedNews(
    val image: String?,
    val title: String?,
    val link: String?,
    val date: String?,
    val provider: String? = "WHO"
)

data class GlobalNews(
    val image: List<GlobalImage>,
    val title: String,
    val description: String,
    val link: String,
    val tags: List<String>,
    val date: String,
    val type: String,
    val locale: String,
    val topics: List<String>,
    val provider: GlobalNewsProvider?
)

data class GlobalImage(
    val url:String,
    val width: String,
    val height: String,
    val title: String,
    val attribution: String
)

data class GlobalNewsProvider(
    val name: String,
    val domain: String,
    val images: String
)

data class LocalNews(
    val image: String,
    val title: String,
    val shortDescription: String,
    val link: String,
    val date: String,
    val provider: String? = "NCDC"
)

data class CountryNews(
    val image: List<GlobalImage>,
    val title: String,
    val description: String,
    val link: String,
    val tags: List<String>,
    val date: String,
    val type: String,
    val locale: String,
    val topics: List<String>,
    val provider: GlobalNewsProvider?
)

data class NigeriaCountryNews(
    val image: String,
    val title: String,
    val link: String,
    val date: String,
    val publisher: String?
)


// RETROFIT CALL RESPONSES

data class RecommendedNewsResponse(
    val error: Boolean,
    val message: String,
    val data: List<RecommendedNews>
)

data class LocalNewsResponse(
    val error: Boolean,
    val message: String,
    val data: List<LocalNews>
)

data class GlobalNewsData(
    val updatedTime: String,
    val news: List<GlobalNews>
)

data class GlobalNewsResponse(
    val error: Boolean,
    val message: String,
    val data: GlobalNewsData
)

data class CountryNewsResponse(
    val error: Boolean,
    val message: String,
    val data: List<CountryNews>
)

data class NigeriaNewsResponse(
    val error: Boolean,
    val message: String,
    val data: List<NigeriaCountryNews>
)
