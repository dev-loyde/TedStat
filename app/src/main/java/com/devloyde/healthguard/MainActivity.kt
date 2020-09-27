package com.devloyde.healthguard

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.devloyde.healthguard.databinding.ActivityMainBinding
import com.devloyde.healthguard.listeners.DisplayListener
import com.devloyde.healthguard.listeners.NavigationListeners
import com.devloyde.healthguard.models.SettingsListItem
import com.devloyde.healthguard.models.StatCountries
import com.devloyde.healthguard.ui.dashboard.CountryListDialogFragment
import com.devloyde.healthguard.ui.dashboard.DashboardViewModel
import com.devloyde.healthguard.ui.home.PreventionDetailFragment
import com.devloyde.healthguard.ui.news.NewsCategoryFragment
import com.devloyde.healthguard.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), SettingsFragment.OnListFragmentInteractionListener,
    NavigationListeners.HomeDetailNavigationListener,
    NavigationListeners.NewsItemUrlNavigationListener,
    DisplayListener.CountrySelection{
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private val tag: String = "MainActivity"
    private lateinit var navController: NavController
    private lateinit var dashboardViewModel: DashboardViewModel

    companion object {
        const val HOMEPAGE_FRAGMENT: String = "HOME_FRAGMENT"
        const val NEWS_FRAGMENT: String = "NEWS FRAGMENT"
        const val DASHBOARD_FRAGMENT: String = "DASHBOARD_FRAGMENT"
        const val SETTINGS_FRAGMENT: String = "SETTINGS_FRAGMENT"


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            bottomNavigationView = navView
        }

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        bottomNavigationView.setupWithNavController(navController)

    }

    override fun onListFragmentInteraction(item: SettingsListItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun launchCustomBrowser(url: String) {

        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
        // builder.addDefaultShareMenuItem("MENU_ITEM_NAME",PendingIntent)
        builder.setShowTitle(true)
//        builder.setCloseButtonIcon(bitmap)
//        builder.setActionButton(bitmap,"Android",pendingIntent,true)
        builder.setExitAnimations(
            this@MainActivity,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
//        val packageName = CustomTabsHelper.getPackageNameToUse(this,url)
//        if(packageName == null){
//            //launch in web view
//        }else{
//
//        }
    }

    override fun navigateToPreventionDetailScreen(position: Int?) {
        if (position != null) {
            val args = PreventionDetailFragment.bundleArgs(position)
//        Toast.makeText(this@MainActivity,position,Toast.LENGTH_LONG).show()
            navController.navigate(R.id.action_navigation_home_to_preventionDetailFragment, args)
        } else {
            navController.navigate(R.id.action_navigation_home_to_preventionDetailFragment)
        }
    }

    override fun launchCustomUrl(url: String) {
        Toast.makeText(this@MainActivity, url, Toast.LENGTH_SHORT).show()
        launchCustomBrowser(url)
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


}
