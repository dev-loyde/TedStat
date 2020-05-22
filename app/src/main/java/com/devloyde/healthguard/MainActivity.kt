package com.devloyde.healthguard

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.devloyde.healthguard.databinding.ActivityMainBinding
import com.devloyde.healthguard.models.FragmentTag
import com.devloyde.healthguard.models.SettingsListItem
import com.devloyde.healthguard.ui.dashboard.DashboardFragment
import com.devloyde.healthguard.ui.home.HomeFragment
import com.devloyde.healthguard.ui.news.NewsCategoryFragment
import com.devloyde.healthguard.ui.news.NewsFragment
import com.devloyde.healthguard.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), SettingsFragment.OnListFragmentInteractionListener,
    NewsCategoryFragment.NewsItemClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private val tag: String = "MainActivity"

    // Fragments stack list
    private var mFragmentTags: ArrayList<String> = ArrayList()
    private var mFragments: ArrayList<FragmentTag> = ArrayList()
    private var mExitCount: Int = 0

    // Fragments
    private var mHomeFragment: HomeFragment? = null
    private var mNewsFragment: NewsFragment? = null
    private var mDashboardFragment: DashboardFragment? = null
    private var mSettingsFragment: SettingsFragment? = null

    // Bottom Navigation Fragment Positions
    private val homeFragmentPosition = 0
    private val newsFragmentPosition: Int = 1
    private val dashboardFragmentPosition: Int = 2
    private val settingsFragmentPosition: Int = 3


    companion object {
        const val HOMEPAGE_FRAGMENT: String = "HOME_FRAGMENT"
        const val NEWS_FRAGMENT: String = "NEWS FRAGMENT"
        const val DASHBOARD_FRAGMENT: String = "DASHBOARD_FRAGMENT"
        const val SETTINGS_FRAGMENT: String = "SETTINGS_FRAGMENT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            bottomNavigationView = navView
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Displays the HOMEPAGE fragment on first load
        displayFragment(HOMEPAGE_FRAGMENT)
    }

    override fun onListFragmentInteraction(item: SettingsListItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // Navigation listener for every menu item click in bottom navigation
    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    displayFragment(
                        HOMEPAGE_FRAGMENT
                    )
                }
                R.id.navigation_news -> displayFragment(
                    NEWS_FRAGMENT
                )
                R.id.navigation_dashboard -> displayFragment(
                    DASHBOARD_FRAGMENT
                )
                R.id.navigation_settings -> displayFragment(
                    SETTINGS_FRAGMENT
                )
            }
            false
        }

    /**
     *  used to display fragment by reusing if it is already in the stack else create a new one
     *  @param fragmentTag tag for each fragment passed to the stack
     */
    private fun displayFragment(fragmentTag: String) {
        lateinit var fragment: Fragment
        when (fragmentTag) {
            HOMEPAGE_FRAGMENT -> {
                if (mHomeFragment == null) {
                    fragment = HomeFragment()
                    mHomeFragment = fragment
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, fragment, fragmentTag)
                        .commit()
                    mFragmentTags.add(fragmentTag)
                    mFragments.add(FragmentTag(fragment, fragmentTag))
                } else {
                    mFragmentTags.remove(fragmentTag)
                    mFragmentTags.add(fragmentTag)
                }
            }
            NEWS_FRAGMENT -> {
                if (mNewsFragment == null) {
                    fragment = NewsFragment()
                    mNewsFragment = fragment
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, fragment, fragmentTag)
                        .commit()
                    mFragmentTags.add(fragmentTag)
                    mFragments.add(FragmentTag(fragment, fragmentTag))
                } else {
                    mFragmentTags.remove(fragmentTag)
                    mFragmentTags.add(fragmentTag)
                }
            }
            DASHBOARD_FRAGMENT -> {
                if (mDashboardFragment == null) {
                    fragment = DashboardFragment()
                    mDashboardFragment = fragment
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, fragment, fragmentTag)
                        .commit()
                    mFragmentTags.add(fragmentTag)
                    mFragments.add(FragmentTag(fragment, fragmentTag))
                } else {
                    mFragmentTags.remove(fragmentTag)
                    mFragmentTags.add(fragmentTag)
                }
            }
            SETTINGS_FRAGMENT -> {
                if (mSettingsFragment == null) {
                    fragment = SettingsFragment()
                    mSettingsFragment = fragment
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, fragment, fragmentTag)
                        .commit()
                    mFragmentTags.add(fragmentTag)
                    mFragments.add(FragmentTag(fragment, fragmentTag))
                } else {
                    mFragmentTags.remove(fragmentTag)
                    mFragmentTags.add(fragmentTag)
                }
            }
        }
        setFragmentVisibilities(fragmentTag)
    }

    /**
     * For Showing/ Hiding the fragments by looping through them based on user interaction with bottom navigation
     * @param fragmentTag tag for the fragment to be displayed
     * {@link #displayFragment(FRAGMENT_TAG) }
     */
    private fun setFragmentVisibilities(fragmentTag: String) {
        for (i in 0 until mFragments.size) {
            if (fragmentTag == mFragments[i].mTag) { // Show
                supportFragmentManager
                    .beginTransaction()
                    .show(mFragments[i].mFragment)
                    .commit()

            } else { // Hide
                supportFragmentManager
                    .beginTransaction()
                    .hide(mFragments[i].mFragment)
                    .commit()
            }
        }
        setNavigationIcon(fragmentTag)
    }

    /**
     *  Sets the Active checked property for each menu item in the bottom navigation based on visibility from
     *  {@link #setFragmentVisibilities(FRAGMENT_TAG) }
     *  @param fragmentTag tag for the fragment which menu item should be checked active
     */
    private fun setNavigationIcon(fragmentTag: String) {
        val menu: Menu = bottomNavigationView.menu
        when (fragmentTag) {
            HOMEPAGE_FRAGMENT -> {
                Log.d(tag, "setNavigationIcon: Home fragment is visible")
                menu.getItem(homeFragmentPosition).isChecked = true
            }
            NEWS_FRAGMENT -> {
                Log.d(tag, "setNavigationIcon: News fragment is visible")
                menu.getItem(newsFragmentPosition).isChecked = true
            }
            DASHBOARD_FRAGMENT -> {
                Log.d(tag, "setNavigationIcon: Dashboard fragment is visible")
                menu.getItem(dashboardFragmentPosition).isChecked = true
            }
            SETTINGS_FRAGMENT -> {
                Log.d(tag, "setNavigationIcon: Account fragment is visible")
                menu.getItem(settingsFragmentPosition).isChecked = true
            }
        }
    }

    /**
     *  Checks the Fragments count in the stack to determine backward movement and when to exit the app
     */
    override fun onBackPressed() {
        val backStackCount = mFragmentTags.size
        if (backStackCount > 1) {
            val topFragmentTag = mFragmentTags[backStackCount - 1]
            val newTopFragmentTag = mFragmentTags[backStackCount - 2]
            setFragmentVisibilities(newTopFragmentTag)
            mFragmentTags.remove(topFragmentTag)
            mExitCount = 0
        } else if (backStackCount == 1) {
            val topFragmentTag = mFragmentTags[backStackCount - 1]
            if (topFragmentTag == HOMEPAGE_FRAGMENT) { //    mHomeFragment.scrollToTop();
                mExitCount++
                Toast.makeText(this@MainActivity, "1 more click to exit", Toast.LENGTH_SHORT).show()
            } else {
                mExitCount++
                Toast.makeText(this@MainActivity, "1 more click to exit", Toast.LENGTH_SHORT).show()
            }
        }
        if (mExitCount >= 2) {
            super.onBackPressed()
        }
    }

    override fun onItemClick(url: String) {

    }

}
