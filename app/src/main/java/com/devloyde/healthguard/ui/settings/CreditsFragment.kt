package com.devloyde.healthguard.ui.settings

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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewpager2.widget.ViewPager2
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.CreditsAdapter
import com.devloyde.healthguard.databinding.FragmentCreditsBinding
import com.devloyde.healthguard.databinding.FragmentHomeBinding
import com.devloyde.healthguard.models.Credits

class CreditsFragment : Fragment() {

    private lateinit var binding: FragmentCreditsBinding
    private lateinit var toolbar: Toolbar
    private lateinit var list: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_credits, container, false)
        bindViews()
        navController = findNavController()
        toolbar.setupWithNavController(navController)
        setUpRecyclerView()
        return binding.root
    }

    private fun bindViews() {
        binding.apply {
            toolbar = creditsToolbar
            list = creditsList
        }
    }

    private fun setUpRecyclerView() {
       list.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
            adapter = CreditsAdapter(creditsData())
        }
    }

    private fun creditsData(): List<Credits> {
        return listOf(
            Credits(categories = "Illustrations, Logo",source = "Freepik",link = "www.com"),
            Credits(categories = "Animations",source = "Lottie",link = "www.com"),
            Credits(categories = "Covid19 Statistics Data",source = "Wikipedia",link = "www.com"),
            Credits(categories = "Symptoms",source = "CDC",link = "www.com"),
            Credits(categories = "Health Care News, Advisory, Faq",source = "NCDC (nigeria)",link = "www.com"),
            Credits(categories = "Recommended News",source = "WHO",link = "www.com"),
            Credits(categories = "Latest and Local News",source = "Bing News",link = "www.com")
        )
    }
}