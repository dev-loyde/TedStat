package com.devloyde.healthguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.R
import com.devloyde.healthguard.databinding.CountryShowcaseBinding
import com.devloyde.healthguard.models.StatCountries
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class DashboardCountryAdapter :
    RecyclerView.Adapter<DashboardCountryAdapter.CountryItemViewHolder>() {
    private var mItems = ArrayList<StatCountries>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryShowcaseBinding.inflate(inflater, parent, false)
        return CountryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        val country: StatCountries = mItems[position]
        holder.bind(country)
    }

    fun addItems(items: ArrayList<StatCountries>) {
        mItems = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class CountryItemViewHolder(val binding: CountryShowcaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: StatCountries) {
            val context = binding.countryName.context
            binding.countryName.text = country.country
            binding.infectedCases.text = context.getString(R.string.countries_stat_infected_cases, country.cases)
            binding.recoveredCases.text = context.getString(R.string.countries_stat_recovered_cases, country.recovered)
            binding.deathCases.text = context.getString(R.string.countries_stat_death_cases, country.deaths)
            Picasso.with(binding.countryFlag.context)
                .load(country.flag)
                .into(binding.countryFlag)

            binding.executePendingBindings()
        }
    }


}
