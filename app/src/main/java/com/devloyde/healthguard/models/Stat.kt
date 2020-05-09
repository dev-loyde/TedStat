package com.devloyde.healthguard.models

data class GlobalStat(
    val updatedTime: String,
    val totalConfirmedCases: Int,
    val newlyConfirmedCases: Int,
    val totalDeaths: Int,
    val newDeaths: Int,
    val totalRecoveredCases: Int,
    val newlyRecoveredCases: Int
)

data class StatLocation(
    val long: Int,
    val lat: Int,
    val countryOrRegion: String,
    val provinceOrState: String?,
    val county: String?,
    val isoCode: String
    )

data class StatCountries(
    val location: StatLocation,
    val totalConfirmedCases: Int,
    val newlyConfirmedCases: Int,
    val totalDeaths: Int,
    val newDeaths: Int,
    val totalRecoveredCases: Int,
    val newlyRecoveredCases: Int
)

data class StatHistory(
    val date: String,
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int
)