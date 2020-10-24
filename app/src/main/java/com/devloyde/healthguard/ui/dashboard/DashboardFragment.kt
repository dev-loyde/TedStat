package com.devloyde.healthguard.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.HomeAdapter
import com.devloyde.healthguard.databinding.FragmentDashboardBinding
import com.devloyde.healthguard.listeners.AppBarStateListener
import com.devloyde.healthguard.listeners.DisplayListener
import com.devloyde.healthguard.listeners.NavigationListeners
import com.devloyde.healthguard.models.*
import com.github.mikephil.charting.animation.Easing.EaseOutCirc
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var countryPieChart: PieChart
    private lateinit var toolbar: Toolbar
    private lateinit var dashboardRv: RecyclerView
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var appBar: AppBarLayout
    private lateinit var currentCountryName: TextView

    private var countrySelectionListener: DisplayListener.CountrySelection? = null
    private var navigationListeners: NavigationListeners.HomeDetailNavigationListener? = null
    private lateinit var viewsAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        binding.lifecycleOwner = this
        bindViews()
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_news,
                R.id.navigation_dashboard,
                R.id.navigation_settings
            )
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)
        appBar.addOnOffsetChangedListener(object : AppBarStateListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {

                if (state.name == "EXPANDED") {
                    collapsingToolbar.isTitleEnabled = false
                    toolbar.title = "Dashboard"
                }
                if (state.name == "COLLAPSED") {
                    collapsingToolbar.isTitleEnabled = true
                    collapsingToolbar.title = binding.dashboardCountryBtn.text
                }
            }
        })
        binding.dashboardCountryBtn.setOnClickListener {
            countrySelectionListener?.showCountrySelectionDialog()
        }
        setUpCountryChart()
        initRecyclerView()
        return binding.root
    }

    private fun bindViews() {
        binding.apply {
            countryPieChart = pieChart
            currentCountryName = pieCaseTitle
            toolbar = dashboardToolbar
            dashboardRv = dashboardContent.dashboardRecyclerView
            collapsingToolbar = dashboardCollapsingToolbar
            appBar = dashboardAppbar
        }

    }

    private fun setUpCountryChart() {

        countryPieChart.setUsePercentValues(true)
        countryPieChart.isDrawHoleEnabled = true
        countryPieChart.setHoleColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        countryPieChart.transparentCircleRadius = 60F
        countryPieChart.legend.isEnabled = false
        countryPieChart.description.isEnabled = false

    }

    private fun insertChartData(pieDataValues: ArrayList<PieEntry>) {
        countryPieChart.animateY(1000, EaseOutCirc)

        val pieDataSet = PieDataSet(pieDataValues, "chart")
        pieDataSet.sliceSpace = 3F
        pieDataSet.selectionShift = 5F
        pieDataSet.colors = listOf(
            ColorTemplate.rgb("#2ecc71"),
            ColorTemplate.rgb("#ff33b5e5"),
            ColorTemplate.rgb("#e9967a")
        )

        //ColorTemplate.PASTEL_COLORS.asList()

        val pieData = PieData(pieDataSet)
        countryPieChart.data = pieData
        countryPieChart.invalidate()
    }

    private fun loadItems() {
        val items = ArrayList<Any>()
        items.add(Loading(true))
        items.add(Loading(true))
        items.add(Loading(true))

        dashboardViewModel.getDefaultCountry()
            .observe(viewLifecycleOwner, Observer { defaultCountry ->
                if (defaultCountry is StatCountries) {
                    currentCountryName.text =
                        getString(R.string.dashboard_cases_country_header, defaultCountry.country)
                    binding.pieCaseNo.text = defaultCountry.cases
                    binding.dashboardCountryBtn.text =
                        defaultCountry.country?.toUpperCase(Locale.getDefault())
                    val countriesData = ArrayList<PieEntry>()
                    if (defaultCountry.recovered!! != "No data") {
                        countriesData.add(
                            PieEntry(
                                parseFloatStat(defaultCountry.recovered) + 10000.0.toFloat(),
                                "R-C"
                            )
                        )
                    }
                    if (defaultCountry.cases!! != "No data") {
                        countriesData.add(
                            PieEntry(
                                parseFloatStat(defaultCountry.cases) - 10000.0.toFloat(),
                                "C-C"
                            )
                        )
                    }
                    if (defaultCountry.deaths!! != "No data") {
                        countriesData.add(
                            PieEntry(
                                parseFloatStat(defaultCountry.deaths) - 10000.0.toFloat(),
                                "T-D"
                            )
                        )
                    }
                    insertChartData(countriesData)
                    val impacts = ImpactStats(
                        listOf(
                            ImpactStat(name = "Confirmed Cases(CC)", count = defaultCountry.cases),
                            ImpactStat(
                                name = "Recovered Cases (RC)",
                                count = defaultCountry.recovered
                            ),
                            ImpactStat(name = "Total Deaths (TD)", count = defaultCountry.deaths)
                        )
                    )
                    items[0] = impacts
                    viewsAdapter.notifyDataSetChanged()
                }
            })

        dashboardViewModel.getCurrentCountry()
            .observe(viewLifecycleOwner, Observer { selectedCountry ->
                if (selectedCountry is StatCountries) {
                    currentCountryName.text =
                        getString(R.string.dashboard_cases_country_header, selectedCountry.country)
                    binding.pieCaseNo.text = selectedCountry.cases
                    binding.dashboardCountryBtn.text =
                        selectedCountry.country?.toUpperCase(Locale.getDefault())
                    val countriesData = ArrayList<PieEntry>()
                    if (selectedCountry.recovered!! != "No data") {
                        countriesData.add(
                            PieEntry(
                                parseFloatStat(selectedCountry.recovered) + 10000.0.toFloat(),
                                "R-C"
                            )
                        )
                    }
                    if (selectedCountry.cases!! != "No data") {
                        countriesData.add(
                            PieEntry(
                                parseFloatStat(selectedCountry.cases) - 10000.0.toFloat(),
                                "C-C"
                            )
                        )
                    }
                    if (selectedCountry.deaths!! != "No data") {
                        countriesData.add(
                            PieEntry(
                                parseFloatStat(selectedCountry.deaths) - 10000.0.toFloat(),
                                "T-D"
                            )
                        )
                    }
                    insertChartData(countriesData)
                    val impacts = ImpactStats(
                        listOf(
                            ImpactStat(name = "Confirmed Cases(CC)", count = selectedCountry.cases),
                            ImpactStat(
                                name = "Recovered Cases (RC)",
                                count = selectedCountry.recovered
                            ),
                            ImpactStat(name = "Total Deaths (TD)", count = selectedCountry.deaths)
                        )
                    )
                    items[0] = impacts
                    viewsAdapter.notifyDataSetChanged()
                }
            })

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
                items[1] = globalStatistics
                //  homeAdapter.addItem(items)
                viewsAdapter.notifyDataSetChanged()
            }
        }

        dashboardViewModel.topAffectedCountriesStat.observe(viewLifecycleOwner) { countries ->
            // GLOBAL STATISTICS
            if (countries is List<StatCountries>) {
                if (countries.size > 1) {
                    val countriesStat = CountriesVerticalRv(
                        title = "Top Affected Countries",
                        countries = countries
                    )
                    items[2] = countriesStat
                }
            }
        }

        viewsAdapter.addItem(items)
    }

    private fun initRecyclerView() {
        viewsAdapter = HomeAdapter(navigationListeners)
        dashboardRv.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        dashboardRv.setHasFixedSize(false)
        dashboardRv.adapter = viewsAdapter
        loadItems()
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

//    private fun parseConverter(stat: Any): List<Int>? {
//        when (stat) {
//            is StatCountries -> {
//                val confirmedCases = stat.cases!!.toIntOrNull() ?: parseStringInt(stat.cases)
//                val recoveredCases =
//                    stat.recovered!!.toIntOrNull() ?: parseStringInt(stat.recovered)
//                val deathCases = stat.deaths!!.toIntOrNull() ?: parseStringInt(stat.deaths)
//
//                val total = confirmedCases + recoveredCases + deathCases
//
//                val confirmedPercentage = confirmedCases / total * 100
//                val recoveredPercentage = recoveredCases / total * 100
//                val deathsPercentage = deathCases / total * 100
//
//                return listOf(confirmedPercentage.toFloat(), recoveredPercentage.toFloat(), deathsPercentage.toFloat())
//            }
//            is GlobalStat -> {
//                val confirmedCases = stat.cases!!.toIntOrNull() ?: parseStringInt(stat.cases)
//                val recoveredCases =
//                    stat.recovered!!.toIntOrNull() ?: parseStringInt(stat.recovered)
//                val deathCases = stat.deaths!!.toIntOrNull() ?: parseStringInt(stat.deaths)
//
//                val total = confirmedCases + recoveredCases + deathCases
//
//                val confirmedPercentage = confirmedCases / total * 100
//                val recoveredPercentage = recoveredCases / total * 100
//                val deathsPercentage = deathCases / total * 100
//
//                return listOf(confirmedPercentage.toFloat(), recoveredPercentage.toFloat(), deathsPercentage.toFloat())
//            }
//        }
//        return null
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is DisplayListener.CountrySelection -> {
                countrySelectionListener = context
            }
            else -> {
                throw RuntimeException("$context must implement countrySelectionListener Dialog")
            }
        }
    }

}