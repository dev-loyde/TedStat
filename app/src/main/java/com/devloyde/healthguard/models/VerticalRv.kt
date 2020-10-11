package com.devloyde.healthguard.models

data class VerticalRv(
    var title: String,
    var verticalItems: List<HealthCard>
)

data class CountriesVerticalRv(
    var title: String,
    var countries: List<StatCountries>
)
