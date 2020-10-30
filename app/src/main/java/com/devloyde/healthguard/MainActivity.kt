package com.devloyde.healthguard

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.devloyde.healthguard.databinding.ActivityMainBinding
import com.devloyde.healthguard.db.SharedPref
import com.devloyde.healthguard.listeners.DisplayListener
import com.devloyde.healthguard.listeners.NavigationListeners
import com.devloyde.healthguard.networking.ConnectivityProvider
import com.devloyde.healthguard.ui.dashboard.CountryListDialogFragment
import com.devloyde.healthguard.ui.dashboard.DashboardViewModel
import com.devloyde.healthguard.ui.home.InfoDetailFragment
import com.devloyde.healthguard.ui.home.PreventionDetailFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import com.tapadoo.alerter.Alerter


class MainActivity : AppCompatActivity(),
    NavigationListeners.HomeDetailNavigationListener,
    NavigationListeners.NewsItemUrlNavigationListener,
    DisplayListener.CountrySelection,
    DisplayListener.UpdateTheme,
    NavigationListeners.SocialsNavigationListener,
    ConnectivityProvider.ConnectivityStateListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private val tag: String = "MainActivity"
    private lateinit var navController: NavController
    private lateinit var dashboardViewModel: DashboardViewModel

    private val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        checkDarkMode()
        super.onCreate(savedInstanceState)
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            bottomNavigationView = navView
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        bottomNavigationView.setupWithNavController(navController)

    }

    private fun checkDarkMode() {
        val sharedPref = SharedPref(applicationContext)
        if (sharedPref.loadDarkModeState()) {
            setTheme(R.style.DarkTheme_NoActionBar)
        } else {
            setTheme(R.style.AppTheme_NoActionBar)
        }
    }


    private fun launchCustomBrowser(url: String) {

        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
        // builder.addDefaultShareMenuItem("MENU_ITEM_NAME",PendingIntent)
        builder.setShowTitle(true)
        builder.setExitAnimations(
            this@MainActivity,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
//        val packageName = CustomTabsIntent..getPackageNameToUse(this,url)
//        if(packageName == null){
//            launchUrlIntentToExternalApps(url)
//        }
    }

    override fun navigateToPreventionDetailScreen(position: Int?) {
        if (position != null) {
            val args = PreventionDetailFragment.bundleArgs(position)
            navController.navigate(R.id.action_navigation_home_to_preventionDetailFragment, args)
        } else {
            navController.navigate(R.id.action_navigation_home_to_preventionDetailFragment)
        }
    }

    override fun launchCustomUrl(url: String) {
        Toast.makeText(this@MainActivity, "loading...", Toast.LENGTH_SHORT).show()
        launchCustomBrowser(url)
    }

    override fun navigateToInfoDetailScreen(infoType: Int, position: Int?) {
        val args = InfoDetailFragment.bundleArgs(infoType)
        navController.navigate(R.id.action_navigation_home_to_infoDetailFragment, args)
    }

    override fun launchNewsUrl(url: String) {
        Toast.makeText(this@MainActivity, url, Toast.LENGTH_SHORT).show()
        launchCustomBrowser(url)
    }

    override fun showCountrySelectionDialog() {
        CountryListDialogFragment().apply {
            show(supportFragmentManager, "country_selection_dialog")
            enterTransition = R.anim.fragment_open_enter
        }
    }

    override fun changeTheme(mode: Boolean) {
        recreate()
    }

    override fun launchSocialFollow(url: String) {
        launchUrlIntentToExternalApps(url)
    }

    private fun launchUrlIntentToExternalApps(url: String) {
        try {
            if (url != "") {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        } catch (e: Exception) {

        }

    }


    private fun showNetworkMessage(isConnected: Boolean) {
        slideUp(false)
        val bgColor: Int = MaterialColors.getColor(
            this, R.attr.colorPrimary,
            ContextCompat.getColor(this, R.color.colorPrimary)
        )

        if (isConnected) {
            binding.networkStateContainer.text = "Connection Back"
            binding.networkStateContainer.setBackgroundColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.colorPrimary,
                    theme
                )
            )
            binding.networkStateContainer.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.darkTextColor,
                    theme
                )
            )
            slideUp(true)
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                slideUp(false)
            }, 2000)
        } else {
            binding.networkStateContainer.text = "No Connection"
            binding.networkStateContainer.setBackgroundColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.cardBackground,
                    theme
                )
            )
            binding.networkStateContainer.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.textColor,
                    theme
                )
            )
            slideUp(true)
        }
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        showNetworkMessage(state.hasInternet())
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
    }

    override fun onStart() {
        super.onStart()
        provider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        provider.removeListener(this)
    }

    private fun slideUp(show: Boolean) {
        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.duration = 600
        transition.addTarget(binding.networkStateContainer)
        TransitionManager.beginDelayedTransition(binding.mainLayout, transition)
        if (show) {
            binding.networkStateContainer.visibility = View.VISIBLE
        } else {
            binding.networkStateContainer.visibility = View.GONE
        }
    }

}
