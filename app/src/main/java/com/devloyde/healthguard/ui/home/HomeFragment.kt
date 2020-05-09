package com.devloyde.healthguard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.HomeAdapter
import com.devloyde.healthguard.adapters.VerticalAdapter
import com.devloyde.healthguard.databinding.FragmentHomeBinding
import com.devloyde.healthguard.models.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeRv: RecyclerView
    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        bindViews()
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun bindViews() {
        binding.apply {
            toolbar = mainToolbar
        }
    }

    private fun initRecyclerView() {

        val homeAdapter = HomeAdapter(allItems())
        binding.homeRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.homeRecyclerView.setHasFixedSize(false)
        binding.homeRecyclerView.adapter = homeAdapter

    }

    private fun allItems(): ArrayList<Any> {

        val verticalRv: VerticalRv
        val horizontalRv: HorizontalRv
        val horizontalRv2: HorizontalRv
        val carousel: Carousels

        val allItems: ArrayList<Any> = ArrayList()
        val verticalInput: ArrayList<HealthCard> = ArrayList()
        val horizontalInput: ArrayList<HorizontalSingle> = ArrayList()
        val horizontalInput2: ArrayList<HorizontalSingle> = ArrayList()
        val carouselItem: ArrayList<Carousel> = ArrayList()

        carouselItem.add(Carousel(title = "Stay warm at all times", image = R.drawable.family))
        carouselItem.add(
            Carousel(
                title = "Stay warm at all times",
                image = R.drawable.social_distance
            )
        )
        carouselItem.add(
            Carousel(
                title = "Healthy lifestyle increase life",
                image = R.drawable.warrior
            )
        )

        carousel = Carousels(carouselItem)

        verticalInput.add(
            HealthCard(
                title = "card 1",
                description = "",
                image = R.drawable.antiseptic
            )
        )
        verticalInput.add(
            HealthCard(
                title = "card 1",
                description = "",
                image = R.drawable.dont_touch
            )
        )
        verticalInput.add(HealthCard(title = "card 1", description = "", image = R.drawable.mask))
        verticalInput.add(
            HealthCard(
                title = "card 1",
                description = "",
                image = R.drawable.men_with_mask
            )
        )
        verticalInput.add(
            HealthCard(
                title = "card 1",
                description = "",
                image = R.drawable.antiseptic
            )
        )
        verticalInput.add(
            HealthCard(
                title = "card 1",
                description = "",
                image = R.drawable.dont_touch
            )
        )
        verticalInput.add(HealthCard(title = "card 1", description = "", image = R.drawable.mask))
        verticalInput.add(
            HealthCard(
                title = "card 1",
                description = "",
                image = R.drawable.men_with_mask
            )
        )

        verticalRv = VerticalRv("Safety Tips", verticalInput)

        horizontalInput.add(
            HorizontalSingle(
                title = "card 1",
                description = "",
                image = R.drawable.antiseptic
            )
        )
        horizontalInput.add(
            HorizontalSingle(
                title = "card 1",
                description = "",
                image = R.drawable.dont_touch
            )
        )
        horizontalInput.add(
            HorizontalSingle(
                title = "card 1",
                description = "",
                image = R.drawable.mask
            )
        )

        horizontalRv = HorizontalRv(null, horizontalInput)

        horizontalInput2.add(
            HorizontalSingle(
                title = "card 1",
                description = "",
                image = R.drawable.antiseptic
            )
        )
        horizontalInput2.add(
            HorizontalSingle(
                title = "card 1",
                description = "",
                image = R.drawable.dont_touch
            )
        )
        horizontalInput2.add(
            HorizontalSingle(
                title = "card 1",
                description = "",
                image = R.drawable.mask
            )
        )

        horizontalRv2 = HorizontalRv(null, horizontalInput2)

        allItems.add(carousel)
        allItems.add(VerticalRv("Safety Tips", verticalInput))
        allItems.add(GlobalStat("time", 2333, 222, 2, 445, 223, 200))
        allItems.add(VerticalRv("Covid-19 Prevention", verticalInput))
        allItems.add(horizontalRv)
        allItems.add(VerticalRv("Home Remedies", verticalInput))
        allItems.add(horizontalRv2)

        return allItems
    }
}