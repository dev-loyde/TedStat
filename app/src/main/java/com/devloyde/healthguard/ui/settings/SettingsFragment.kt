package com.devloyde.healthguard.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.SettingsAdapter
import com.devloyde.healthguard.databinding.FragmentNewsBinding
import com.devloyde.healthguard.databinding.FragmentSettingsItemListBinding
import com.devloyde.healthguard.listeners.NavigationListeners
import com.devloyde.healthguard.models.SettingsListItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.rd.draw.data.Orientation

class SettingsFragment : Fragment(), NavigationListeners.SettingsNavigationListener {

    private lateinit var binding: FragmentSettingsItemListBinding
    private lateinit var list: RecyclerView
    private lateinit var toolbar: Toolbar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

        val navController = findNavController()
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
        list.adapter = SettingsAdapter(
            listOf(
                SettingsListItem(R.drawable.ic_share_black_24dp, "Share App", null),
                SettingsListItem(R.drawable.ic_library, "Open Source Licenses", null),
                SettingsListItem(R.drawable.ic_about_us, "About", null),
                SettingsListItem(R.drawable.ic_dark_mode, "Night Mode", 1),
                SettingsListItem(R.drawable.ic_info_black_24dp, "Help", null),
                SettingsListItem(R.drawable.ic_star_black_24dp, "Rate Us", null)
            )
            ,this
        )
        return binding.root
    }

    override fun launchOpenSourceLicenses() {
        val licenseIntent = Intent(activity, OssLicensesMenuActivity::class.java)
        startActivity(licenseIntent)
    }


}
