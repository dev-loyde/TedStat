package com.devloyde.tedstat.ui.dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devloyde.tedstat.R
import com.devloyde.tedstat.adapters.CountryAdapter
import com.devloyde.tedstat.databinding.FragmentCountryListDialogBinding
import com.devloyde.tedstat.listeners.DisplayListener
import com.devloyde.tedstat.models.StatCountries

class CountryListDialogFragment : BottomSheetDialogFragment(),
    DisplayListener.UpdateCountrySelection {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var adapter: CountryAdapter

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
        adapter = CountryAdapter(this)
        binding.countryList.adapter = adapter
        dashboardViewModel =
            ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        dashboardViewModel.countriesStat.observe(viewLifecycleOwner, Observer { countries ->
            if (countries is List<StatCountries>) {
                val sortedCountries = countries.sortedBy { it.country }
                adapter.addItems(sortedCountries.toCollection(ArrayList()))
                searchCountry()
            }
        })
        binding.countryCloseBtn.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun updateSelectedCountry(country: StatCountries) {
        dashboardViewModel.setCurrentCountry(country)
    }

    private fun searchCountry() {
        val countrySearchTextWatcher: TextWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(
                searchInput: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                adapter.filter.filter(searchInput)
            }
        }
        val countrySearchView = binding.countrySearch
        countrySearchView.addTextChangedListener(countrySearchTextWatcher)
    }
}