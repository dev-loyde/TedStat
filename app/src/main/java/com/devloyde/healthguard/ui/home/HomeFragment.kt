package com.devloyde.healthguard.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.HomeAdapter
import com.devloyde.healthguard.databinding.FragmentHomeBinding
import com.devloyde.healthguard.listeners.HomeDetailNavigationListener
import com.devloyde.healthguard.models.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeRv: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    private var homeDetailNavigationListener: HomeDetailNavigationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        bindViews()
        navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        initRecyclerView()
        homeViewModel.countriesStat.observe(viewLifecycleOwner){ countriesStat ->
            Log.d("HOME_FRAGMENT", "fetched global stat")
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is HomeDetailNavigationListener -> {
                homeDetailNavigationListener = context
            }
            else -> {
                throw RuntimeException("$context must implement navigateToPreventionDetailScreen")
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        homeDetailNavigationListener = null
    }

    private fun bindViews() {
        binding.apply {
            toolbar = mainToolbar
        }
    }

    private fun initRecyclerView() {

        val homeAdapter = HomeAdapter(allItems(),homeDetailNavigationListener)
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
                Carousel(title = "Healthy lifestyle increase life", image = R.drawable.warrior),
                Carousel(title = "Stay warm at all times", image = R.drawable.smiley),
                Carousel(title = "Stay warm at all times", image = R.drawable.social_distance)
            )
        )

        //   SAFETY ITEMS INPUT
        val safetyTipsList = VerticalRv(
            "Prevention Safety Tips",
            listOf(
                HealthCard(
                    title = "WEAR A FACE MASK",
                    description = "The wearing of face masks is recommended in order to prevent " +
                            "those who are infected from but asymptomatic from spreading the virus." +
                            " Improper handling could lead to infections",
                    image = R.drawable.cover_your_nose
                ),
                HealthCard(
                    title = "DONT TOUCH EYES NOSE OR MOUTH WITH UNWASHED HANDS",
                    description = "Avoid touching your eyes nose and mouth with unwashed" +
                            " hands Normal practices like greeting one another" +
                            " with handshakes or hugging should be avoided",
                    image = R.drawable.touch_safety
                ),
                HealthCard(
                    title = "WASH YOUR HANDS FREQUENTLY",
                    description = "Regularly and thoroughly wash your " +
                            "hands with soap and running water or use of alcohol-based" +
                            " sanitizers if water is not available",
                    image = R.drawable.wash_hands_safety_tip
                ), HealthCard(
                    title = "COUGH ETIQUETTE Cover your Mouth With Sleeve Or Elbow",
                    description = "If someone is coughing or sneezing, " +
                            "prompt them to cover their cough with a disposable tissue and " +
                            "discard in a waste bin and washing their hands or using their elbow bent",
                    image = R.drawable.sneezing_safety
                ),
                HealthCard(
                    title = "CLEAN AND DISINFECT",
                    description = "If someone is coughing or sneezing, " +
                            "prompt them to cover their cough with a disposable tissue and " +
                            "discard in a waste bin and washing their hands or using their elbow bent",
                    image = R.drawable.disinfectant
                ),
                HealthCard(
                    title = "AVOID CONTACT WITH SICK PEOPLE",
                    description = "If someone is coughing or sneezing, " +
                            "prompt them to cover their cough with a disposable tissue and " +
                            "discard in a waste bin and washing their hands or using their elbow bent",
                    image = R.drawable.avoid_contact_safety
                )
            )
        )

        // GLOBAL STATISTICS
        val globalStat = GlobalStat(3, "time", 2333,
            222, 2, 445, 223, 200)

        // What you should know
        val awareness = HorizontalSingle(
            "what you should know about covid-19",
            "https://www.healthline.com/health/coronavirus-covid-19",
            R.raw.covid_virus
        )

        // What you should know about face mask
        val faceMask = HorizontalSingle(
            "Guidelines to use of face mask",
            "https://cdc.gov/coronavirus/2019-ncov/prevent-getting-sick/cloth-face-cover-guidance.html",
            R.raw.how_wear_mask
        )

        val symptoms = HorizontalBanner(
            "Symptoms",
            "https://www.cdc.gov/coronavirus/2019-ncov/symptoms-testing/symptoms.html",
            R.drawable.symptoms_covid
        )

        // Games for children
        val games = HorizontalSingle(
            "Games for children to keeps your home alive",
            "http://www.google.com",
            R.raw.ninja_kids_istayhome
        )

        val advisory = InfoRv(
            title = "Advisory",
            infoItems = listOf(

                "TRAVEL ADVICE",
                "TRAVELLERS TO NIGERIA",
                "ADVICE FOR HEALTH WORKERS",
                "ADVICE FOR BUSINESSES",
                "HOW TO PROTECT YOUR SELF"
            ),
            source = "NCDC",
            sourceLink = "www.ncdc.com"
        )

        val faq = InfoRv(
            title = "FAQ",
            infoItems = listOf(
                "What is covid-19",
                "What is the source of covid-19",
                "is covid-19 airborne?",
                "Are countries with covid-19 immune to covid-19"
            ),
            source = "NCDC",
            sourceLink = "www.ncdc.com"
        )

        allItems.addAll(
            listOf(
                carouselItemList,
                safetyTipsList,
                globalStat,
                awareness,
                symptoms,
                faceMask,
                advisory,
                games,
                faq
            )
        )

        return allItems
    }


}