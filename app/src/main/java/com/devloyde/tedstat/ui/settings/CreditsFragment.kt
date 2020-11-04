package com.devloyde.tedstat.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.devloyde.tedstat.R
import com.devloyde.tedstat.adapters.CreditsAdapter
import com.devloyde.tedstat.databinding.FragmentCreditsBinding
import com.devloyde.tedstat.databinding.FragmentHomeBinding
import com.devloyde.tedstat.listeners.NavigationListeners
import com.devloyde.tedstat.models.Credits

class CreditsFragment : Fragment() {

    private lateinit var binding: FragmentCreditsBinding
    private lateinit var toolbar: Toolbar
    private lateinit var list: RecyclerView
    private lateinit var navController: NavController
    private var listener: NavigationListeners.SocialsNavigationListener? = null

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
            adapter = CreditsAdapter(creditsData(), listener)
        }
    }

    private fun creditsData(): List<Credits> {
        return listOf(
            Credits(categories = "Illustrations, Logo", source = "Freepik", link = "https://freepik.com"),
            Credits(categories = "Animations", source = "Lottie", link = "https://www.lottiefiles.com"),
            Credits(categories = "Covid19 Statistics Data", source = "Wikipedia", link = "https://en.wikipedia.org/wiki/Template:COVID-19_pandemic_data"),
            Credits(categories = "Symptoms", source = "CDC", link = "https://www.cdc.gov/coronavirus/2019-ncov/symptoms-testing/symptoms.html"),
            Credits(categories = "Health Care News, Advisory, Faq",source = "NCDC (nigeria)", link = "https://covid19.ncdc.gov.ng/globals"),
            Credits(categories = "Recommended News", source = "WHO", link = "https://who.int"),
            Credits(categories = "Latest and Local News", source = "Bing News", link = "")
        )

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationListeners.SocialsNavigationListener) {
            listener = context
        }
    }

}