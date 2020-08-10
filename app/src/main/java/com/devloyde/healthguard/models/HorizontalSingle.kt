package com.devloyde.healthguard.models

import com.devloyde.healthguard.listeners.HomeDetailNavigationListener

data class HorizontalSingle(
    var title: String,
    var link: String?,
    var image: Int
)

data class HorizontalBanner(
    var title: String,
    var link: String?,
    var image: Int
)