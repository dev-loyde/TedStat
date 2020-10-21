package com.devloyde.healthguard.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Loading(val loading: Boolean)

@Entity
data class TimeoutCheck(
    @PrimaryKey
    var id: Int,
    val timeout: Long = System.currentTimeMillis(),
    val name: String
)
