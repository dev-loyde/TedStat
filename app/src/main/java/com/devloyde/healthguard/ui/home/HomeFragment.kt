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
import com.devloyde.healthguard.listeners.NavigationListeners
import com.devloyde.healthguard.models.*
import com.devloyde.healthguard.ui.dashboard.DashboardViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController
    private var navigationListeners: NavigationListeners.HomeDetailNavigationListener? = null
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        dashboardViewModel =
            ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        bindViews()
        navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        initRecyclerView()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is NavigationListeners.HomeDetailNavigationListener -> {
                navigationListeners = context
            }
            else -> {
                throw RuntimeException("$context must implement navigateToPreventionDetailScreen")
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationListeners = null
    }

    private fun bindViews() {
        binding.apply {
            toolbar = mainToolbar
        }
    }

    private fun initRecyclerView() {
        homeAdapter = HomeAdapter(navigationListeners)
        binding.homeRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.homeRecyclerView.setHasFixedSize(false)
        binding.homeRecyclerView.adapter = homeAdapter
        loadItems()
    }

    private fun loadItems() {
        val items = arrayListOf<Any>()
        //     HOME CAROUSEL INPUT
        val carouselItemList = Carousels(
            listOf(
                Carousel(title = "Healthy lifestyle increase life", image = R.drawable.warrior),
                Carousel(title = "Stay warm at all times", image = R.drawable.smiley),
                Carousel(title = "Stay warm at all times", image = R.drawable.social_distance)
            )
        )
        items.add(carouselItemList)
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
        items.add(safetyTipsList)

        items.add(Loading(true))
        dashboardViewModel.globalStat.observe(viewLifecycleOwner) { globalStat ->
            // GLOBAL STATISTICS
            if (globalStat is GlobalStat) {
                val total =
                    parseIntegerStat(globalStat.cases!!) + parseIntegerStat(globalStat.recovered!!) +
                            parseIntegerStat(globalStat.deaths!!)
                val globalCasesProgress =
                    parseGlobalStat(globalStat.cases, total) + parseGlobalStat(
                        globalStat.deaths, total
                    ) +
                            parseGlobalStat(globalStat.recovered, total)
                val globalRecoveredProgress =
                    parseGlobalStat(globalStat.recovered, total) + parseGlobalStat(
                        globalStat.deaths, total
                    ) + 15
                val globalDeathsProgress = parseGlobalStat(globalStat.deaths, total) + 20

                val globalStatistics = GlobalStat(
                    globalStat.id,
                    globalStat.cases,
                    globalStat.recovered,
                    globalStat.deaths,
                    globalCasesProgress,
                    globalRecoveredProgress,
                    globalDeathsProgress
                )
                items[2] = globalStatistics
                homeAdapter.notifyItemChanged(2)
            }
        }

        // What you should know
        val awareness = HorizontalSingle(
            "what you should know about covid-19",
            "https://www.healthline.com/health/coronavirus-covid-19",
            R.raw.covid_virus
        )
        items.add(awareness)

        items.add(Loading(true))
        dashboardViewModel.topAffectedCountriesStat.observe(viewLifecycleOwner) { countries ->
            // GLOBAL STATISTICS
            if (countries is List<StatCountries>) { //To prevent crash in case result is empty or null
                if(countries.size > 1) {
                    val countriesStat = CountriesVerticalRv(
                        title = "Top Affected Countries",
                        countries = countries
                    )
                    items[4] = countriesStat
                    homeAdapter.notifyItemChanged(4)
                }
            }
        }

        // What you should know about face mask
        val faceMask = HorizontalSingle(
            "Guidelines to use of face mask",
            "https://cdc.gov/coronavirus/2019-ncov/prevent-getting-sick/cloth-face-cover-guidance.html",
            R.raw.how_wear_mask
        )
        items.add(faceMask)

        val symptoms = HorizontalBanner(
            "Symptoms",
            "https://www.cdc.gov/coronavirus/2019-ncov/symptoms-testing/symptoms.html",
            R.drawable.symptoms_covid
        )
        items.add(symptoms)

        items.add(Loading(true))
        homeViewModel.advisoryInfo.observe(viewLifecycleOwner) { advisoryInfo ->
            // GLOBAL STATISTICS
            if (advisoryInfo is List<AdvisoryInfo>) {
                if (advisoryInfo.size > 3) {
                    val advisory = InfoRv(
                        title = "Advisory",
                        infoItems = advisoryInfo.subList(0, 3)
                    )
                    items[7] = advisory
                    homeAdapter.notifyItemChanged(7)
                }
            }
        }

        items.add(Loading(true))
        homeViewModel.faqInfo.observe(viewLifecycleOwner) { faqInfo ->
            // GLOBAL STATISTICS
            if (faqInfo is List<FaqInfo>) {
                if (faqInfo.size > 3) {
                    val faq = InfoRv(
                        title = "Faq",
                        infoItems = faqInfo.subList(0, 3)
                    )
                    items[8] = faq
                    homeAdapter.notifyItemChanged(8)
                }
            }
        }

        homeAdapter.addItem(items)
    }

    private fun parseIntegerStat(text: String): Int {
        return text.replace(Regex(","), "").toInt()
    }

    private fun parseFloatStat(text: String): Float {
        return text.replace(Regex(","), "").toFloat()
    }

    private fun parseGlobalStat(stat: String, total: Int): Int {
        return ((parseFloatStat(stat) / total) * 100).toInt()
    }


}