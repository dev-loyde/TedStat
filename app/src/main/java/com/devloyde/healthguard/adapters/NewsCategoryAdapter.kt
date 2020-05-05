package com.devloyde.healthguard.adapters

import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.databinding.NewsCategoryListItemBinding

import com.devloyde.healthguard.databinding.VerticalSingleItemBinding
import com.devloyde.healthguard.models.News
import com.devloyde.healthguard.models.NewsContent


class NewsCategoryAdapter(
    private var mItems: List<String>
    //   private var restaurantItemClickListener: RestaurantItemClick
) : RecyclerView.Adapter<NewsCategoryAdapter.NewsCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsCategoryViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsCategoryListItemBinding.inflate(inflater, parent, false)
        return NewsCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsCategoryViewHolder, position: Int) {
        val news = mItems[position]
        holder.bind(news as String)

    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class NewsCategoryViewHolder(val binding: NewsCategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {

            binding.executePendingBindings()
        }


    }


}
