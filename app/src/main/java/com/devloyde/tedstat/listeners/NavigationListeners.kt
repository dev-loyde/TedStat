package com.devloyde.tedstat.listeners

import android.widget.ImageView

class NavigationListeners {

    interface HomeDetailNavigationListener {
        fun navigateToPreventionDetailScreen(
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
