package com.devloyde.healthguard.models

import androidx.fragment.app.Fragment

data class FragmentTag(
    val mFragment: Fragment,
    val mTag: String
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