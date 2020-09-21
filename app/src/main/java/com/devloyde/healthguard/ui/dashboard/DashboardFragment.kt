package com.devloyde.healthguard.ui.dashboard

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.ImpactAdapter
import com.devloyde.healthguard.databinding.FragmentDashboardBinding
import com.devloyde.healthguard.listeners.AppBarStateListener
import com.devloyde.healthguard.models.GlobalStat
import com.devloyde.healthguard.models.ImpactStat
import com.github.mikephil.charting.animation.Easing.EaseOutCirc
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var countryPieChart: PieChart
    private lateinit var toolbar: Toolbar
    private lateinit var impactRv: RecyclerView
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var appBar: AppBarLayout

    private lateinit var globalCases: TextView
    private lateinit var globalRecovered: TextView
    private lateinit var globalDeaths: TextView

    private lateinit var globalCasesProgress: ProgressBar
    private lateinit var globalRecoveredProgress: ProgressBar
    private lateinit var globalDeathsProgress: ProgressBar

    private lateinit var impactAdapter: ImpactAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
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
                    collapsingToolbar.title = "Nigeria"
                }
            }
        })
        return binding.root
    }


    private fun bindViews() {
        binding.apply {
            countryPieChart = pieChart
            toolbar = dashboardToolbar
            impactRv = dashboardContent.dashboardImpactRecyclerView
            collapsingToolbar = dashboardCollapsingToolbar
            appBar = dashboardAppbar
        }
        //BIND GLOBAL STAT VIEWS
        binding.apply {
            globalCases = dashboardContent.globalCasesValue
            globalRecovered = dashboardContent.recoveredCasesValue
            globalDeaths = dashboardContent.deathsCasesValue
            //PROGRESS BARS
            globalCasesProgress = dashboardContent.globalCasesProgress
            globalRecoveredProgress = dashboardContent.globalRecoveredProgress
            globalDeathsProgress = dashboardContent.globalDeathsProgress
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCountryStat()
        setUpImpactStat()

        loadSelectedCountry()
    }

    private fun setUpCountryStat() {

        countryPieChart.setUsePercentValues(true)
        countryPieChart.isDrawHoleEnabled = true
        countryPieChart.setHoleColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
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

        //ColorTemplate.PASTEL_COLORS.asList()

        val pieData = PieData(pieDataSet)
        countryPieChart.data = pieData
        countryPieChart.invalidate()
    }

    private fun setUpImpactStat() {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        impactAdapter = ImpactAdapter()
        impactRv.layoutManager = layoutManager
        impactRv.adapter = impactAdapter
    }

    private fun loadSelectedCountry() {
        dashboardViewModel.getCountry("Nigeria").observe(viewLifecycleOwner, Observer { country ->
            if (country != null) {
                binding.pieCaseNo.text = country.cases
                insertChartData(
                    listOf(
                        PieEntry(parseFloatStat(country.recovered!!) + 10000.0.toFloat(), "R-C"),
                        PieEntry(parseFloatStat(country.cases!!) - 10000.0.toFloat(), "C-C"),
                        PieEntry(parseFloatStat(country.deaths!!) - 10000.0.toFloat(), "T-D")
                    )
                )
                val list = ArrayList<ImpactStat>()
                list.add(ImpactStat(name = "Confirmed Cases(CC)", count = country.cases))
                list.add(ImpactStat(name = "Recovered Cases (RC)", count = country.recovered))
                list.add(ImpactStat(name = "Total Deaths (TD)", count = country.deaths))
                impactAdapter.addItems(list)

            }
        })
        dashboardViewModel.globalStat.observe(viewLifecycleOwner, Observer { globalStat ->
            if(globalStat != null) {
                val globalStatistics: GlobalStat = globalStat[0]
                globalCases.text = globalStatistics.cases
                globalRecovered.text = globalStatistics.recovered
                globalDeaths.text = globalStatistics.deaths

                val total = parseIntegerStat(globalStatistics.cases!!) + parseIntegerStat(globalStatistics.recovered!!) +
                                 parseIntegerStat(globalStatistics.deaths!!)
                globalCasesProgress.progress = parseGlobalStat(globalStatistics.cases, total) + parseGlobalStat(globalStatistics.deaths, total) +
                                               parseGlobalStat(globalStatistics.recovered, total)
                globalRecoveredProgress.progress = parseGlobalStat(globalStatistics.recovered, total) + parseGlobalStat(globalStatistics.deaths, total) + 10
                globalDeathsProgress.progress = parseGlobalStat(globalStatistics.deaths, total) + 20
            }
        })
    }

    private fun parseIntegerStat(text: String): Int {
        return text.replace(Regex(","), "").toInt()
    }

    private fun parseFloatStat(text: String): Float {
        return text.replace(Regex(","), "").toFloat()
    }

    private fun parseGlobalStat(stat: String,total: Int): Int {
        return  ((parseFloatStat(stat)/total)* 100).toInt()
    }
}