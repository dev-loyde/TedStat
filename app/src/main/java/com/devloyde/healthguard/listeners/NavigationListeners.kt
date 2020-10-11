package com.devloyde.healthguard.listeners

import com.devloyde.healthguard.models.HealthCard

class NavigationListeners {

    interface HomeDetailNavigationListener {
        fun navigateToPreventionDetailScreen(position: Int? = null)

        fun launchCustomUrl(url: String)

        fun navigateToInfoDetailScreen(position: Int? = null)
    }

    interface NewsItemUrlNavigationListener {
        fun launchNewsUrl(url: String)
    }
}
