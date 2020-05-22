package com.devloyde.healthguard.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.NewsFragmentPagerAdapter
import com.devloyde.healthguard.databinding.FragmentNewsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class NewsFragment : Fragment() {

    companion object {
       lateinit var toolbar: Toolbar

        const val RECOMMENDED_NEWS: String = "RECOMMENDED_NEWS"
        const val LOCAL_NEWS: String = "LOCAL_NEWS"
        const val GLOBAL_NEWS: String = "GLOBAL_NEWS"
        const val COUNTRY_NEWS: String = "COUNTRY_NEWS"
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsFragmentPagerAdapter: NewsFragmentPagerAdapter
    private lateinit var newsTabLayout: TabLayout
    private lateinit var newsViewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        newsTabLayout = binding.newsTabLayout
        newsViewPager = binding.newsViewPager
        toolbar = binding.newsToolbar

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNewsFragmentPager()


    }


    private fun initNewsFragmentPager() {
        newsFragmentPagerAdapter =
            NewsFragmentPagerAdapter(childFragmentManager, lifecycle)

        addNewsFragmentPagerData()

        binding.newsViewPager.adapter = newsFragmentPagerAdapter

        TabLayoutMediator(newsTabLayout, newsViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Recommended"
                1 -> tab.text = "Latest"
                2 -> tab.text = "For You"
                3 -> tab.text = "Health Care"
            }
        }.attach()
    }

    private var recommendedFragmentInstance = NewsCategoryFragment.newInstance(RECOMMENDED_NEWS)
    private var globalFragmentInstance = NewsCategoryFragment.newInstance(GLOBAL_NEWS)
    private var localFragmentInstance = NewsCategoryFragment.newInstance(LOCAL_NEWS)
    private var healthCareFragmentInstance = NewsCategoryFragment.newInstance(COUNTRY_NEWS)


    private fun addNewsFragmentPagerData() {

        newsFragmentPagerAdapter.addFragment(recommendedFragmentInstance)
        newsFragmentPagerAdapter.addFragment(globalFragmentInstance)
        newsFragmentPagerAdapter.addFragment(healthCareFragmentInstance)
        newsFragmentPagerAdapter.addFragment(localFragmentInstance)
    }

}

