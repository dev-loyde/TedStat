package com.devloyde.healthguard.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GlobalStat(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val cases: String?,
    val recovered: String?,
    val deaths: String?,
    val casesProgress: Int? = 0,
    val recoveredProgress: Int? = 0,
    val deathsProgress: Int? = 0
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

