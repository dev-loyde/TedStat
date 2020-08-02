package com.devloyde.healthguard.models


data class ImpactStat(
    val name: String,
    val count: Int
)

data class Banner(
    var id: Int,
    var title: String?,
    var image: String
)

data class Carousel(
    var id: Int = 1,
    var title: String?,
    var image: Int
)

data class Carousels(var carousels: List<Carousel>)


data class Welcomes(var welcomes: List<Welcome>)



data class SettingsListItem(
    var icon:Int,
    var title: String,
    var pref: Int?
)