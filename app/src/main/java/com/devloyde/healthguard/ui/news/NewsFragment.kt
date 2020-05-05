package com.devloyde.healthguard.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.NewsFragmentPagerAdapter
import com.devloyde.healthguard.databinding.FragmentNewsBinding
import com.devloyde.healthguard.models.News
import com.devloyde.healthguard.models.WhoRssFeed
import com.devloyde.healthguard.networking.JsonServiceBuilder
import com.devloyde.healthguard.networking.NewsEndpoints

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
   private lateinit var newsFragmentPagerAdapter: NewsFragmentPagerAdapter
    private lateinit var newsTabLayout: TabLayout
    private lateinit var newsViewPager: ViewPager2
//    private lateinit var newsResponse: News

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        newsTabLayout =  binding.newsTabLayout
        newsViewPager = binding.newsViewPager

          return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNewsFragmentPager()

        //   fetchNews()
    }

    private fun fetchNews() {
        val request = JsonServiceBuilder.buildService(NewsEndpoints::class.java)
        val call = request.getNews("US")
        call.enqueue(object : Callback<News> {
            override fun onFailure(call: Call<News>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun initNewsFragmentPager() {
        newsFragmentPagerAdapter =
            NewsFragmentPagerAdapter(childFragmentManager, lifecycle)
        addNewsFragmentPagerData()

        binding.newsViewPager.adapter = newsFragmentPagerAdapter

        TabLayoutMediator(newsTabLayout,newsViewPager) { tab, position ->
               when (position) {
                    0 -> tab.text = "Recommended"
                    1 -> tab.text = "Latest"
                    2 -> tab.text = "For You"
                    3 -> tab.text = "Health Care"
                }
            }.attach()
    }

    private fun latestCategory(): NewsCategoryFragment {
        val fragment = NewsCategoryFragment()
        val args = Bundle()
        //      args.putParcelableArrayList("first_category", verticalItems())
        fragment.arguments = args
        return fragment
    }

    private fun recommendedCategory(): NewsCategoryFragment {
        val fragment = NewsCategoryFragment()
        val args = Bundle()
        //      args.putParcelableArrayList("first_category", verticalItems())
        fragment.arguments = args
        return fragment
    }

    private fun forYouCategory(): NewsCategoryFragment {
        val fragment = NewsCategoryFragment()
        val args = Bundle()
        //      args.putParcelableArrayList("first_category", verticalItems())
        fragment.arguments = args
        return fragment
    }

    private fun healthCareCategory(): NewsCategoryFragment {
        val fragment = NewsCategoryFragment()
        val args = Bundle()
        //      args.putParcelableArrayList("first_category", verticalItems())
        fragment.arguments = args
        return fragment
    }

    private fun addNewsFragmentPagerData() {

        newsFragmentPagerAdapter.addFragment(recommendedCategory())
        newsFragmentPagerAdapter.addFragment(latestCategory())
        newsFragmentPagerAdapter.addFragment(forYouCategory())
        newsFragmentPagerAdapter.addFragment(healthCareCategory())
    }
}
