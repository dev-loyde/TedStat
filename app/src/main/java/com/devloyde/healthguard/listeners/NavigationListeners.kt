package com.devloyde.healthguard.listeners

import com.devloyde.healthguard.models.HealthCard

class NavigationListeners {

    interface HomeDetailNavigationListener {
        fun navigateToPreventionDetailScreen(position: Int? = null)

        fun launchCustomUrl(url: String)

        fun navigateToInfoDetailScreen(infoType:Int,position: Int? = null)

    }

    interface NewsItemUrlNavigationListener {
        fun launchNewsUrl(url: String)
    }

    interface SettingsNavigationListener {
        fun launchOpenSourceLicenses()
        fun launchShare()
    }
}
