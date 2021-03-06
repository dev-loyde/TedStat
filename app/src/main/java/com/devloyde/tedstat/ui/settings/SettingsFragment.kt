package com.devloyde.tedstat.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.devloyde.tedstat.R
import com.devloyde.tedstat.adapters.SettingsAdapter
import com.devloyde.tedstat.databinding.FragmentNewsBinding
import com.devloyde.tedstat.databinding.FragmentSettingsItemListBinding
import com.devloyde.tedstat.db.SharedPref
import com.devloyde.tedstat.listeners.DisplayListener
import com.devloyde.tedstat.listeners.NavigationListeners
import com.devloyde.tedstat.models.SettingsListItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class SettingsFragment : Fragment(), NavigationListeners.SettingsNavigationListener {

    private lateinit var binding: FragmentSettingsItemListBinding
    private lateinit var list: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    private lateinit var sharedPref: SharedPref
    private lateinit var themeListener: DisplayListener.UpdateTheme

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings_item_list,
            container,
            false
        )
        binding.apply {
            toolbar = settingsToolbar
            list = settingsList
        }

        navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_news,
                R.id.navigation_dashboard,
                R.id.navigation_settings
            )
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)

        list.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
        }

        sharedPref = SharedPref(list.context)

        list.adapter = SettingsAdapter(
            sharedPref,
            listOf(
                SettingsListItem(R.drawable.ic_share_black_24dp, "Share With Friends", null),
                SettingsListItem(R.drawable.ic_library, "Open Source Licenses", null),
                SettingsListItem(R.drawable.ic_library, "Credits", null),
                SettingsListItem(R.drawable.ic_about_us, "About", null),
                SettingsListItem(R.drawable.ic_dark_mode, "Dark Mode", 1),
                SettingsListItem(R.drawable.ic_info_black_24dp, "FeedBack", null)
            )
            , this
        )
        return binding.root
    }

    override fun launchOpenSourceLicenses() {
        val licenseIntent = Intent(activity, OssLicensesMenuActivity::class.java)
        startActivity(licenseIntent)
    }

    override fun launchCredits() {
        navController.navigate(R.id.action_navigation_settings_to_creditsFragment)
    }

    override fun launchAbout() {
        navController.navigate(R.id.action_navigation_settings_to_aboutFragment)
    }

    override fun launchFeedBack() {
        navController.navigate(R.id.action_navigation_settings_to_feedBackFragment)
    }

    override fun launchShare() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "TedStat")
            shareIntent.putExtra(
                Intent.EXTRA_TEXT, "Hi download TedStat today. " +
                        "A simple covid19 information aggregator app that helps you stay " +
                        "informed on news and statistics from the world and around you." +
                        " https://bit.ly/tedstat"
            )
            startActivity(Intent.createChooser(shareIntent, "Share with :"))
        } catch (e: Exception) {
            Toast.makeText(
                activity,
                "Sorry, \nCannot share please try again later",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DisplayListener.UpdateTheme) {
            themeListener = context
        }
    }

    override fun restartApp(mode: Boolean) {
        if (mode) {
            sharedPref.setDarkModeState(true)
        } else {
            sharedPref.setDarkModeState(false)
        }
        themeListener.changeTheme(mode)
    }

}
