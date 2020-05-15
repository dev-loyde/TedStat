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

        val allItems: ArrayList<Any> = ArrayList()

        //     HOME CAROUSEL INPUT
        val carouselItemList = Carousels(
            listOf(
                Carousel(title = "Stay warm at all times", image = R.drawable.family),
                Carousel(
                    title = "Stay warm at all times",
                    image = R.drawable.social_distance
                ),
                Carousel(
                    title = "Healthy lifestyle increase life",
                    image = R.drawable.warrior
                )
            )
        )

        //   SAFETY ITEMS INPUT
        val safetyTipsList = VerticalRv(
            "Safety Tips", listOf(
                HealthCard(
                    title = "Sanitizers",
                    description = "Regularly and thoroughly wash your " +
                            "hands with soap and running water or use of alcohol-based" +
                            " sanitizers if water is not available",
                    image = R.drawable.antiseptic
                ),
                HealthCard(
                    title = "Touch",
                    description = "Avoid touching your eyes nose and mouth with unwashed" +
                            " hands <br> Normal practices like greeting one another" +
                            " with handshakes or hugging should be avoided",
                    image = R.drawable.dont_touch
                ),
                HealthCard(
                    title = "Face masks",
                    description = "The wearing of face masks is recommended in order to prevent" +
                            "those who are infected from but asymptomatic from spreading the virus." +
                            " and others from contacting.Properly dispose of the mask in waste bins." +
                            "Improper handling could lead to infections",
                    image = R.drawable.mask
                ),
                HealthCard(
                    title = "Disposable Tissue",
                    description = "If someone is coughing or sneezing, " +
                            "prompt them to cover their cough with a disposable tissue and " +
                            "discard in a waste bin and washing their hands or using their elbow bent",
                    image = R.drawable.toilet_paper
                )
            )
        )

        // GLOBAL STATISTICS
        val globalStat = GlobalStat("time", 2333, 222, 2, 445, 223, 200)

        // What you should know
        val awareness = HorizontalSingle("what you should know about covid-19","coronavirus is a blah blah blah",R.raw.covid_virus)

        // Transmission
        val transmission = VerticalRv(
            "Safety Tips", listOf(
                HealthCard(
                    title = "Sanitizers",
                    description = "Regularly and thoroughly wash your " +
                            "hands with soap and running water or use of alcohol-based" +
                            " sanitizers if water is not available",
                    image = R.drawable.antiseptic
                ),
                HealthCard(
                    title = "Touch",
                    description = "Avoid touching your eyes nose and mouth with unwashed" +
                            " hands <br> Normal practices like greeting one another" +
                            " with handshakes or hugging should be avoided",
                    image = R.drawable.dont_touch
                ),
                HealthCard(
                    title = "Face masks",
                    description = "The wearing of face masks is recommended in order to prevent" +
                            "those who are infected from but asymptomatic from spreading the virus." +
                            " and others from contacting.Properly dispose of the mask in waste bins." +
                            "Improper handling could lead to infections",
                    image = R.drawable.mask
                ),
                HealthCard(
                    title = "Disposable Tissue",
                    description = "If someone is coughing or sneezing, " +
                            "prompt them to cover their cough with a disposable tissue and " +
                            "discard in a waste bin and washing their hands or using their elbow bent",
                    image = R.drawable.mask
                )
            )
        )

        // Games for children
        val games = HorizontalSingle("Games for children to keeps your home alive","coronavirus is a blah blah blah",R.raw.ninja_kids_istayhome)

        allItems.addAll(
            listOf(
                carouselItemList,
                safetyTipsList,
                globalStat,
                awareness,
                games
            )
        )

        return allItems
    }
}