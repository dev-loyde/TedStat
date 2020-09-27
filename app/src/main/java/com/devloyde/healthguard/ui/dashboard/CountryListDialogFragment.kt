package com.devloyde.healthguard.ui.dashboard

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.CountryAdapter
import com.devloyde.healthguard.databinding.FragmentCountryListDialogBinding
import com.devloyde.healthguard.listeners.DisplayListener
import com.devloyde.healthguard.models.StatCountries
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class CountryListDialogFragment : BottomSheetDialogFragment(),
    DisplayListener.UpdateCountrySelection {

    private lateinit var dashboardViewModel: DashboardViewModel

    lateinit var binding: FragmentCountryListDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_country_list_dialog,
            container,
            false
        )
        loadCountriesList()
        return binding.root
    }


    private fun loadCountriesList() {
        binding.countryList.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter = CountryAdapter(this)
        binding.countryList.adapter = adapter
        dashboardViewModel =
            ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        dashboardViewModel.countriesStat.observe(viewLifecycleOwner, Observer { countries ->
            if (countries is List<StatCountries>) {
                adapter.addItems(countries)
            }
        })
        binding.countryCloseBtn.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun updateSelectedCountry(country: StatCountries) {
        dashboardViewModel.setCurrentCountry(country)
    }

}