package com.devloyde.tedstat.listeners

import android.widget.ImageView
import com.devloyde.tedstat.models.HealthCard

class NavigationListeners {

    interface HomeDetailNavigationListener {
        fun navigateToPreventionDetailScreen(
            item: HealthCard? = null,
            position: Int? = null,
            image: ImageView? = null
        )

        fun launchCustomUrl(url: String)

        fun navigateToInfoDetailScreen(infoType: Int, position: Int? = null)

    }

    interface NewsItemUrlNavigationListener {
        fun launchNewsUrl(url: String)
    }

    interface SocialsNavigationListener {
        fun launchSocialFollow(url: String)
    }

    interface SettingsNavigationListener {
        fun launchOpenSourceLicenses()
        fun launchShare()
        fun restartApp(mode: Boolean)
        fun launchCredits()
        fun launchAbout()
        fun launchFeedBack()
    }
}
