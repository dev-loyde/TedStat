package com.devloyde.healthguard.models

import androidx.room.PrimaryKey

data class Info(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String
)

data class InfoResponse(
    val error: Boolean,
    val message: String,
    val data: List<Info>
)