package com.devloyde.tedstat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.devloyde.tedstat.databinding.ActivityMainBinding
import com.devloyde.tedstat.db.SharedPref
import com.devloyde.tedstat.listeners.DisplayListener
import com.devloyde.tedstat.listeners.NavigationListeners
import com.devloyde.tedstat.models.HealthCard
import com.devloyde.tedstat.networking.ConnectivityProvider
import com.devloyde.tedstat.ui.dashboard.CountryListDialogFragment
import com.devloyde.tedstat.ui.dashboard.DashboardViewModel
import com.devloyde.tedstat.ui.home.InfoDetailFragment
import com.devloyde.tedstat.ui.home.PreventionDetailFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute


class MainActivity : AppCompatActivity(),
    NavigationListeners.HomeDetailNavigationListener,
    NavigationListeners.NewsItemUrlNavigationListener,
    DisplayListener.CountrySelection,
    DisplayListener.UpdateTheme,
    NavigationListeners.SocialsNavigationListener,
    ConnectivityProvider.ConnectivityStateListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
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
        AppCenter.start(
            application,
            BuildConfig.AppCenterKey,
            Distribute::class.java,
            Analytics::class.java,
            Crashes::class.java
        )

        provider.addListener(this)
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

    override fun navigateToPreventionDetailScreen(
        item:HealthCard?,position: Int?,image: ImageView?) {
        var extras: Navigator.Extras? = null
        if (image != null) {
            extras = FragmentNavigatorExtras(image to item!!.image.toString())
        }
        if (position != null) {
            val args = PreventionDetailFragment.bundleArgs(position,item!!)
            navController.navigate(
                R.id.action_navigation_home_to_preventionDetailFragment,
                args, // Bundle of args
                null, // NavOptions
                extras
            )
        } else {
            navController.navigate(
                R.id.action_navigation_home_to_preventionDetailFragment,
                null, // Bundle of args
                null, // NavOptions
                null
            )
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
        //   Toast.makeText(this@MainActivity, url, Toast.LENGTH_SHORT).show()
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
        val context = binding.networkStateContainer.context

        if (isConnected) {
            binding.networkStateContainer.text = getString(R.string.connectivity_connection_back)
            binding.networkStateContainer.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.darkTextColor
                )
            )
            binding.networkStateContainer.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimary
                )
            )
            slideUp(true)
            Handler(Looper.getMainLooper()).postDelayed({
                slideUp(false)
            }, 2000)
        } else {
            binding.networkStateContainer.text = getString(R.string.connectivity_no_connection)
            val textColor: Int = MaterialColors.getColor(
                context, R.attr.textColor,
                ContextCompat.getColor(context, R.color.textColor)
            )
            val bgColor: Int = MaterialColors.getColor(
                context, R.attr.backgroundColor,
                ContextCompat.getColor(context, R.color.backgroundColor)
            )
            binding.networkStateContainer.setTextColor(textColor)
            binding.networkStateContainer.setBackgroundColor(bgColor)
            slideUp(true)
        }
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        showNetworkMessage(state.hasInternet())
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
    }

    override fun onDestroy() {
        super.onDestroy()
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
