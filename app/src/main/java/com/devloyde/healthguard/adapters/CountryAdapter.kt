package com.devloyde.healthguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.databinding.CountryListDialogItemBinding
import com.devloyde.healthguard.listeners.DisplayListener
import com.devloyde.healthguard.listeners.NavigationListeners
import com.devloyde.healthguard.models.ImpactStat
import com.devloyde.healthguard.models.StatCountries

class CountryAdapter(val listener: DisplayListener.UpdateCountrySelection?) : RecyclerView.Adapter<CountryAdapter.CountryItemViewHolder>() {
    private var mItems = ArrayList<StatCountries>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryListDialogItemBinding.inflate(inflater,parent,false)
        return CountryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        val country: StatCountries = mItems[position]
        holder.bind(country,listener)
    }


    fun addItems(items: List<StatCountries>) {
        mItems = items as ArrayList<StatCountries>
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class CountryItemViewHolder(val binding: CountryListDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: StatCountries,listener: DisplayListener.UpdateCountrySelection?){
            binding.countryName.text = country.country
            itemView.setOnClickListener {
                listener?.updateSelectedCountry(country)
            }
            binding.executePendingBindings()
        }
    }

}
