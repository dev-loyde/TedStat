package com.devloyde.healthguard.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GlobalStat(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val updatedTime: String?,
    val totalConfirmedCases: Int?,
    val newlyConfirmedCases: Int?,
    val totalDeaths: Int?,
    val newDeaths: Int?,
    val totalRecoveredCases: Int?,
    val newlyRecoveredCases: Int?
)


@Entity
data class StatCountries(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val countryOrRegion: String?,
    val provinceOrState: String?,
    val isoCode: String?,
    val totalConfirmedCases: Int?,
    val newlyConfirmedCases: Int?,
    val totalDeaths: Int?,
    val newDeaths: Int?,
    val totalRecoveredCases: Int?,
    val newlyRecoveredCases: Int?
)

@Entity
data class StatHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String?,
    val confirmed: Int?,
    val deaths: Int?,
    val recovered: Int?
)