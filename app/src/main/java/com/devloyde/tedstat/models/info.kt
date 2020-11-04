package com.devloyde.tedstat.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AdvisoryInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String
)

@Entity
data class FaqInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String
)

data class Info(
    val id: Int,
    val title: String,
    val description: String
)

data class Credits(
    val categories: String,
    val source: String,
    val link: String
)
