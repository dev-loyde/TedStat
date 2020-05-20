package com.devloyde.healthguard

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.devloyde.healthguard.databinding.ActivityMainBinding
import com.devloyde.healthguard.models.FragmentTag
import com.devloyde.healthguard.models.SettingsListItem
import com.devloyde.healthguard.ui.dashboard.DashboardFragment
import com.devloyde.healthguard.ui.home.HomeFragment
import com.devloyde.healthguard.ui.news.NewsFragment
import com.devloyde.healthguard.ui.settings.SettingsFragment


class MainActivity : AppCompatActivity(), SettingsFragment.OnListFragmentInteractionListener {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val HOMEPAGE_FRAGMENT: String = "HOME_FRAGMENT"
        const val NEWS_FRAGMENT: String = "NEWS FRAGMENT"
        const val DASHBOARD_FRAGMENT: String = "DASHBOARD_FRAGMENT"
        const val SETTINGS_FRAGMENT: String = "SETTINGS_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setupWithNavController(navController)
    }

    override fun onListFragmentInteraction(item: SettingsListItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
