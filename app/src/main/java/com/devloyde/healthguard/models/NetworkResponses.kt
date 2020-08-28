package com.devloyde.healthguard.models

// GLOBAL STATISTICS NETWORK RESPONSE OBJECT
data class GlobalStatResponse(
    val error: Boolean,
    val message: String,
    val data: GlobalStat
)

// COUNTRY STATISTICS NETWORK RESPONSE OBJECT
data class CountryStatResponse(
    val error: Boolean,
    val message: String,
    val data: List<StatCountries>
)

// COUNTRY STATISTICS NETWORK RESPONSE OBJECT
data class HistoryStatResponse(
    val error: Boolean,
    val message: String,
    val data: List<StatHistory>
)

// ADVISORY/FAQ NETWORK RESPONSE OBJECT
data class AdvisoryResponse(
    val error: Boolean,
    val message: String,
    val data: List<AdvisoryInfo>
)

data class FaqResponse(
    val error: Boolean,
    val message: String,
    val data: List<FaqInfo>
)