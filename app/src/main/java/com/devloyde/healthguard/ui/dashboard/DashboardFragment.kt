package com.devloyde.healthguard.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCountryStat()
        setUpImpactStat()
    }


    private fun setUpCountryStat() {

        countryPieChart.setUsePercentValues(true)
        countryPieChart.isDrawHoleEnabled = true
        countryPieChart.setHoleColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        countryPieChart.transparentCircleRadius = 60F
        countryPieChart.legend.isEnabled = false
        countryPieChart.description.isEnabled = false
//        countryPieChart.maxAngle = 180F
//        countryPieChart.rotationAngle = 270F

        val pieDataValues = listOf(
            PieEntry(80F, "N-C"),
            PieEntry(100F, "T-C"),
            PieEntry(50F, "N-R"),
            PieEntry(80F, "T-R"),
            PieEntry(100F, "N-D"),
            PieEntry(50F, "T-D")
        )
        countryPieChart.animateY(1000, EaseOutCirc)

        val pieDataSet = PieDataSet(pieDataValues, "chart")
        pieDataSet.sliceSpace = 3F
        pieDataSet.selectionShift = 5F
        pieDataSet.colors = ColorTemplate.JOYFUL_COLORS.asList()

        val pieData = PieData(pieDataSet)
        countryPieChart.data = pieData
        countryPieChart.invalidate()

    }

    private fun setUpImpactStat() {
        impactRv.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = ImpactAdapter(
                listOf(
                    ImpactStat(name = "Confirmed (TC)", count = 200),
                    ImpactStat(name = "Newly Confirmed (NC)", count = 200),
                    ImpactStat(name = "Recovered (TR))", count = 200),
                    ImpactStat(name = "Newly Recovered (NR)", count = 200),
                    ImpactStat(name = "Deaths (TD)", count = 200),
                    ImpactStat(name = "New Deaths (ND)", count = 200)
                )
            )
        }
    }

}