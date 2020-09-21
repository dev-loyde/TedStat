package com.devloyde.healthguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.databinding.ImpactStatItemBinding
import com.devloyde.healthguard.models.ImpactStat

class ImpactAdapter: RecyclerView.Adapter<ImpactAdapter.ImpactStatViewHolder>() {
    private var mItems = ArrayList<ImpactStat>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImpactStatViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ImpactStatItemBinding.inflate(inflater, parent, false)
        return ImpactStatViewHolder(binding)
    }

    fun addItems(items: ArrayList<ImpactStat>) {
        mItems = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ImpactStatViewHolder, position: Int) {
        holder.bind(mItems[position])
    }

    inner class ImpactStatViewHolder(private val binding: ImpactStatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImpactStat){
            binding.impactTitle.text = item.name
            binding.impactValue.text = item.count.toString()
            binding.executePendingBindings()
        }
    }

}
