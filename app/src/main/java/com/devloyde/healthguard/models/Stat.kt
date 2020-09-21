package com.devloyde.healthguard.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GlobalStat(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val cases: String?,
    val recovered: String?,
    val deaths: String?
)


@Entity
data class StatCountries(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val flag: String?,
    val country: String?,
    val cases: String?,
    val recovered: String?,
    val deaths: String?
)

