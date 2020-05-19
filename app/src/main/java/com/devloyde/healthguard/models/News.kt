package com.devloyde.healthguard.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class NewsCard(
    val image: String?,
    val title: String?,
    val link: String?,
    val date: String?,
    val provider: String?
)
//3600000
@Entity
data class TimeoutCheck(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val timeout: Long = System.currentTimeMillis() + 120000,
    val name: String
)

@Entity
data class RecommendedNews(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String?,
    val title: String?,
    val link: String?,
    val date: String?,
    val provider: String? = "WHO"
)

@Entity
data class GlobalNews(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String?,
    val title: String?,
    val link: String?,
    val provider: String?
)

@Entity
data class LocalNews(
     @PrimaryKey(autoGenerate = true)
     val id: Int,
    val image: String?,
    val title: String?,
    val link: String?,
    val date: String?,
    val provider: String? = "NCDC"
)

@Entity
data class CountryNews(
     @PrimaryKey(autoGenerate = true)
     val id: Int,
    val image: String?,
    val title: String?,
    val link: String?,
    val provider: String?
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

data class GlobalNewsResponse(
    val error: Boolean,
    val message: String,
    val data: List<GlobalNews>
)

data class CountryNewsResponse(
    val error: Boolean,
    val message: String,
    val data: List<CountryNews>
)
