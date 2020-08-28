package com.devloyde.healthguard.models

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