package com.devloyde.healthguard.models

data class News(
    var location: NewsLocation,
    var updatedDateTime: String,
    var news: List<NewsContent>
)

data class NewsLocation(
    val countryOrRegion: String,
    val provinceOrState: String,
    val county: String,
    val isoCode: String,
    val lat: String,
    val long: String
)

data class NewsContent(
    val path: String,
    val title: String,
    val excerpt: String,
    val heat: Int,
    val tags: List<String>,
    val type: String,
    val webUrl: String,
    val ampWebUrl: String,
    val cdnAmpWebUrl: String,
    val publishedDateTime: String,
    val updatedDateTime: String,
    val provider: NewsProvider,
    val images: NewsImages,
    val locale: String,
    val categories: List<String>,
    val topics: List<String>
)

data class NewsProvider(
    val name: String,
    val domain: String
)

data class NewsImages(
    val url: String,
    val width: String,
    val height: String,
    val title: String
)