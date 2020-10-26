package com.devloyde.healthguard.models

data class Banner(var id: Int,var title: String?,var image: String)

data class Banners(var banners: ArrayList<Banner>)

data class Carousel(var id: Int = 1,var title: String?,var image: Int)

data class Carousels(var carousels: List<Carousel>)

data class ImpactStat(val name: String,val count: String?,val colour:Int)

data class ImpactStats(var impacts: List<ImpactStat>)

data class Welcomes(var welcomes: List<Welcome>)

data class SettingsListItem(var icon:Int,var title: String,var pref: Int?)