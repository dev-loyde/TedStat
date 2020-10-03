package com.devloyde.healthguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.databinding.CountryListDialogItemBinding
import com.devloyde.healthguard.listeners.DisplayListener
import com.devloyde.healthguard.listeners.NavigationListeners
import com.devloyde.healthguard.models.ImpactStat
import com.devloyde.healthguard.models.StatCountries
import java.util.*
import kotlin.collections.ArrayList

class CountryAdapter(val listener: DisplayListener.UpdateCountrySelection?) :
    RecyclerView.Adapter<CountryAdapter.CountryItemViewHolder>(),Filterable {
    private var mItems = ArrayList<StatCountries>()
    private var mItemsAll = ArrayList<StatCountries>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryListDialogItemBinding.inflate(inflater,parent,false)
        return CountryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        val country: StatCountries = mItems[position]
        holder.bind(country,listener)
    }

    fun addItems(items: ArrayList<StatCountries>) {
        mItems = items
        mItemsAll = ArrayList(items)
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

    override fun getFilter(): Filter {
       return countryFilter
    }

    private val countryFilter: Filter = object: Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<StatCountries>()
            if(constraint.toString().isEmpty()){
                filteredList.addAll(mItemsAll)
            }else{
                for(item in mItemsAll) {
                    if(item.country!!.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT))) {
                        filteredList.add(item)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
           mItems.clear()
            if (results != null) {
                mItems.addAll(results.values as Collection<StatCountries>)
                notifyDataSetChanged()
            }
        }

    }
}
