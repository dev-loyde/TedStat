package com.devloyde.tedstat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.tedstat.databinding.HorizontalSingleItemBinding

import com.devloyde.tedstat.models.HorizontalSingle

class HorizontalAdapter(private val mItems: List<HorizontalSingle>): RecyclerView.Adapter<HorizontalAdapter.HorizontalInnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalInnerViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = HorizontalSingleItemBinding.inflate(inflater, parent, false)
        return HorizontalInnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalInnerViewHolder, position: Int) {
        val horizontalItems: HorizontalSingle = mItems[position]

        holder.bind(horizontalItems)

    }


    override fun getItemCount(): Int {
        return mItems.size
    }


    inner class HorizontalInnerViewHolder(private val binding: HorizontalSingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HorizontalSingle){
//            binding.horizontalItem = item
//            binding.executePendingBindings()
        }


    }

}