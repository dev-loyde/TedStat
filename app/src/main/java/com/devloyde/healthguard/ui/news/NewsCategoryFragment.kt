package com.devloyde.healthguard.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.NewsCategoryAdapter
import com.devloyde.healthguard.databinding.FragmentNewsCategoryBinding
import com.devloyde.healthguard.models.CountryNews
import com.devloyde.healthguard.models.NewsCard
import com.devloyde.healthguard.models.NigeriaCountryNews
import com.devloyde.healthguard.ui.news.NewsFragment.Companion.GLOBAL_NEWS
import com.devloyde.healthguard.ui.news.NewsFragment.Companion.HEALTH_CARE_NEWS
import com.devloyde.healthguard.ui.news.NewsFragment.Companion.LOCAL_NEWS
import com.devloyde.healthguard.ui.news.NewsFragment.Companion.RECOMMENDED_NEWS

/**
 * A simple [Fragment] subclass.
 * Use the [NewsCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsCategoryFragment : Fragment() {
    private lateinit var newsType: String
    private lateinit var binding: FragmentNewsCategoryBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsType = it.getString("FRAGMENT")!!
        }

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_category, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsCategoryAdapter()
        binding.newsCategoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = newsAdapter
        }
        getCategoryData(newsType)
    }

    private fun getCategoryData(fragment: String) {
        when (fragment) {
            RECOMMENDED_NEWS -> {
                viewModel.recommendedNews.observe(viewLifecycleOwner) { recommendedNews ->
                   newsAdapter.addItems(recommendedNews)
                }
            }
            GLOBAL_NEWS -> {
                viewModel.globalNews.observe(viewLifecycleOwner) { globalNews ->
                    newsAdapter.addItems(globalNews)
                }
            }
            LOCAL_NEWS -> {
                val isoCode = "NG"
                viewModel.countryNews.observe(viewLifecycleOwner) { countryNews ->
                    newsAdapter.addItems(countryNews)
                }
            }
            HEALTH_CARE_NEWS -> {
                viewModel.localNews.observe(viewLifecycleOwner) { localNews ->
                    newsAdapter.addItems(localNews)
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param fragment Parameter 1.
         * @return A new instance of fragment NewsCategoryFragment.
         */
        @JvmStatic
        fun newInstance(fragment: String) =
            NewsCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString("FRAGMENT", fragment)
                }
            }
    }
}
