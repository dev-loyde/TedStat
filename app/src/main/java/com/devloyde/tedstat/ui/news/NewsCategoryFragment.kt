package com.devloyde.tedstat.ui.news

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.tedstat.R
import com.devloyde.tedstat.adapters.NewsCategoryAdapter
import com.devloyde.tedstat.databinding.FragmentNewsCategoryBinding
import com.devloyde.tedstat.listeners.NavigationListeners.NewsItemUrlNavigationListener
import com.devloyde.tedstat.ui.news.NewsFragment.Companion.GLOBAL_NEWS
import com.devloyde.tedstat.ui.news.NewsFragment.Companion.COUNTRY_NEWS
import com.devloyde.tedstat.ui.news.NewsFragment.Companion.LOCAL_NEWS
import com.devloyde.tedstat.ui.news.NewsFragment.Companion.RECOMMENDED_NEWS
import com.tapadoo.alerter.Alerter


class NewsCategoryFragment : Fragment() {
    private lateinit var newsType: String
    private lateinit var binding: FragmentNewsCategoryBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsCategoryAdapter
    private var newsItemUrlNavigationListener: NewsItemUrlNavigationListener? = null

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
        binding.newsRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                binding.newsRefreshLayout.isRefreshing = false
                Alerter.create(activity)
                    .setText("Refreshed")
                    .setIcon(R.drawable.ic_check)
                    .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }, 6000)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsCategoryAdapter(newsItemUrlNavigationListener)
        binding.newsCategoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = newsAdapter
        }
        binding.shimmerEffectFrame.startShimmer()
        getCategoryData(newsType)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is NewsItemUrlNavigationListener -> {
                newsItemUrlNavigationListener = context
            }
            else -> {
                throw RuntimeException("$context must implement newsItemUrlNavigationListener")
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        newsItemUrlNavigationListener = null
    }

    private fun getCategoryData(fragment: String) {
        when (fragment) {
            RECOMMENDED_NEWS -> {
                viewModel.recommendedNews.observe(viewLifecycleOwner) { recommendedNews ->
                    if (recommendedNews.isNotEmpty()) {
                        dismissRefreshLayout()
                        binding.shimmerEffectFrame.stopShimmer()
                        binding.shimmerEffectFrame.visibility = View.GONE
                    }
                    newsAdapter.addItems(recommendedNews)
                }
            }
            GLOBAL_NEWS -> {
                viewModel.globalNews.observe(viewLifecycleOwner) { globalNews ->
                    if (globalNews.isNotEmpty()) {
                        dismissRefreshLayout()
                        binding.shimmerEffectFrame.stopShimmer()
                        binding.shimmerEffectFrame.visibility = View.GONE
                    }
                    newsAdapter.addItems(globalNews)
                }
            }
            COUNTRY_NEWS -> {
                val isoCode = "NG"
                viewModel.countryNews.observe(viewLifecycleOwner) { countryNews ->
                    if (countryNews.isNotEmpty()) {
                        dismissRefreshLayout()
                        binding.shimmerEffectFrame.stopShimmer()
                        binding.shimmerEffectFrame.visibility = View.GONE
                    }
                    newsAdapter.addItems(countryNews)
                }
            }
            LOCAL_NEWS -> {
                viewModel.localNews.observe(viewLifecycleOwner) { localNews ->
                    if (localNews.isNotEmpty()) {
                        dismissRefreshLayout()
                        binding.shimmerEffectFrame.stopShimmer()
                        binding.shimmerEffectFrame.visibility = View.GONE
                    }
                    newsAdapter.addItems(localNews)
                }
            }
        }
    }

    private fun dismissRefreshLayout() {
        if (binding.newsRefreshLayout.isRefreshing) {
            binding.newsRefreshLayout.isRefreshing = false
        }
    }

    interface NewsItemClick {
        fun onItemClick(url: String)
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
