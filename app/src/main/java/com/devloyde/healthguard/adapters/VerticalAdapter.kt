package com.devloyde.healthguard.adapters

import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.devloyde.healthguard.databinding.VerticalSingleItemBinding
import com.devloyde.healthguard.models.HealthCard


class VerticalAdapter(
    private var mItems: List<HealthCard>
    //   private var restaurantItemClickListener: RestaurantItemClick
) : RecyclerView.Adapter<VerticalAdapter.VerticalInnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalInnerViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = VerticalSingleItemBinding.inflate(inflater,parent,false)
        return VerticalInnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalInnerViewHolder, position: Int) {
        val verticalModel: HealthCard = mItems[position]
  holder.bind(verticalModel)

    }


    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class VerticalInnerViewHolder(val binding: VerticalSingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HealthCard){
            binding.item = item
            binding.executePendingBindings()
        }


    }


}
