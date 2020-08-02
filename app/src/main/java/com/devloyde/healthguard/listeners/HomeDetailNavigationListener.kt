package com.devloyde.healthguard.listeners

import com.devloyde.healthguard.models.HealthCard

interface HomeDetailNavigationListener {
    fun navigateToPreventionDetailScreen(position: Int? = null)

    fun launchCustomUrl(url:String)
}
