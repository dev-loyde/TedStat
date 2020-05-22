package com.devloyde.healthguard.ui.dashboard

import android.graphics.Color
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
import com.devloyde.healthguard.R
import com.devloyde.healthguard.databinding.FragmentDashboardBinding
import com.devloyde.healthguard.ui.home.HomeFragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding
    lateinit var countryPieChart: PieChart

    companion object {
        lateinit var toolbar: Toolbar

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCountryStat()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        bindViews()

        return binding.root
    }

    private fun bindViews() {
        binding.apply {
            countryPieChart = dashboardPieChart
        }
    }

    fun setUpCountryStat() {
        val colorArray = listOf(
        Color.GRAY,
        Color.MAGENTA,
        Color.WHITE,
        Color.BLACK,
        Color.CYAN,
        Color.BLUE
        )

        val pieDataValues = listOf(
           PieEntry(10F, "Total Confirmed"),
            PieEntry(10F, "Newly Confirmed"),
            PieEntry(100F, "Total Recovered"),
            PieEntry(100F, "Newly Recovered"),
            PieEntry(20F, "Deaths"),
            PieEntry(20F, "Total Deaths")

        )
        val pieDataSet = PieDataSet(pieDataValues,"chart")
        pieDataSet.colors = colorArray

        val pieData = PieData(pieDataSet)
        countryPieChart.data = pieData
        countryPieChart.invalidate()

    }


}