package com.devloyde.healthguard.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.HomeAdapter
import com.devloyde.healthguard.databinding.FragmentDashboardBinding
import com.devloyde.healthguard.listeners.AppBarStateListener
import com.devloyde.healthguard.listeners.DisplayListener
import com.devloyde.healthguard.listeners.NavigationListeners
import com.devloyde.healthguard.models.*
import com.devloyde.healthguard.utils.StatUtils
import com.github.mikephil.charting.animation.Easing.EaseOutCirc
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.color.MaterialColors
import com.tapadoo.alerter.Alerter
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
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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
        swipeRefreshLayout.setOnRefreshListener {
            dashboardViewModel.refresh()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
                Alerter.create(activity)
                    .setText("Refreshed")
                    .setIcon(R.drawable.ic_check)
                    .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }, 6000)
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
            swipeRefreshLayout = dashboardContent.dashboardRefreshLayout
        }

    }

    private fun setUpCountryChart() {

        countryPieChart.setUsePercentValues(true)
        countryPieChart.isDrawHoleEnabled = true
        val color: Int = MaterialColors.getColor(
            countryPieChart.context, R.attr.colorPrimary,
            ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        )
        countryPieChart.setHoleColor(color)
        countryPieChart.transparentCircleRadius = 60F
        countryPieChart.legend.isEnabled = false
        countryPieChart.description.isEnabled = false

    }

    private fun insertChartData(pieDataValues: List<PieEntry>) {
        countryPieChart.animateY(1000, EaseOutCirc)

        val pieDataSet = PieDataSet(pieDataValues, "chart")
        pieDataSet.sliceSpace = 3F
        pieDataSet.selectionShift = 5F
        pieDataSet.colors = listOf(
            ColorTemplate.rgb("#2ecc71"),
            ColorTemplate.rgb("#ff33b5e5"),
            ColorTemplate.rgb("#e9967a")
        )

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
                    val statFloatData = StatUtils.parseCountriesStat(defaultCountry)
                    val countriesData = listOf(
                        PieEntry(statFloatData[0], "R-C"),
                        PieEntry(statFloatData[1], "C-C"),
                        PieEntry(statFloatData[2], "D-C")
                    )
                    insertChartData(countriesData)
                    val impacts = ImpactStats(
                        listOf(
                            ImpactStat(
                                name = "Confirmed Cases(CC)",
                                count = defaultCountry.cases,
                                colour = R.color.colorBlue
                            ),
                            ImpactStat(
                                name = "Recovered Cases (RC)",
                                count = defaultCountry.recovered,
                                colour = R.color.colorPrimary
                            ),
                            ImpactStat(
                                name = "Total Deaths (TD)",
                                count = defaultCountry.deaths,
                                colour = R.color.colorAccent
                            )
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
                    val statFloatData = StatUtils.parseCountriesStat(selectedCountry)
                    val countriesData = listOf(
                        PieEntry(statFloatData[0], "R-C"),
                        PieEntry(statFloatData[1], "C-C"),
                        PieEntry(statFloatData[2], "D-C")
                    )
                    insertChartData(countriesData)
                    val impacts = ImpactStats(
                        listOf(
                            ImpactStat(
                                name = "Confirmed Cases(CC)",
                                count = selectedCountry.cases,
                                colour = R.color.colorBlue
                            ),
                            ImpactStat(
                                name = "Recovered Cases (RC)",
                                count = selectedCountry.recovered,
                                colour = R.color.colorPrimary
                            ),
                            ImpactStat(
                                name = "Total Deaths (TD)",
                                count = selectedCountry.deaths,
                                colour = R.color.colorAccent
                            )
                        )
                    )
                    items[0] = impacts
                    viewsAdapter.notifyDataSetChanged()
                }
            })

        dashboardViewModel.globalStat.observe(viewLifecycleOwner) { globalStat ->
            // GLOBAL STATISTICS
            if (globalStat is GlobalStat) {
                dismissRefreshLayout()
                items[1] = StatUtils.parseGlobalStat(globalStat)
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

    private fun dismissRefreshLayout(){
        if(swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = false
        }
    }

}