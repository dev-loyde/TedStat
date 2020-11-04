package com.devloyde.tedstat.models

data class HealthCard(var title: String?,var description: String?,var image: Int)

data class PreventionDetailCard(var title: String?, var description: String?, var image: Int)

data class VerticalRv(var title: String,var verticalItems: List<HealthCard>)

data class CountriesVerticalRv(var title: String,var countries: List<StatCountries>)
