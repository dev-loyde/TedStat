package com.devloyde.healthguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.databinding.InfoListItemBinding
import com.devloyde.healthguard.databinding.LoadingPlaceholderViewBinding
import com.devloyde.healthguard.listeners.NavigationListeners
import com.devloyde.healthguard.models.*

class InfoAdapter(
    private var mItems: List<Any>,
    private var listener: NavigationListeners.HomeDetailNavigationListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val advisoryInfo: Int = 0
    private val faqInfo: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding
        val holder: RecyclerView.ViewHolder
        when (viewType) {
            advisoryInfo -> {
                binding = InfoListItemBinding.inflate(inflater, parent, false)
                holder = AdvisoryInfoItemViewHolder(binding)
            }
            faqInfo -> {
                binding = InfoListItemBinding.inflate(inflater, parent, false)
                holder = FaqInfoItemViewHolder(binding)
            }
            else -> {
                binding = LoadingPlaceholderViewBinding.inflate(inflater, parent, false)
                holder = HomeAdapter.LoadingViewHolder(binding)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
             advisoryInfo -> {
                 val info = mItems[position] as AdvisoryInfo
                 val advisoryInfoViewHolder = holder as AdvisoryInfoItemViewHolder
                 advisoryInfoViewHolder.bind(info,position)
             }
            faqInfo -> {
                val info = mItems[position] as FaqInfo
                val faqInfoViewHolder = holder as FaqInfoItemViewHolder
                faqInfoViewHolder.bind(info,position)
            }
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {
        if (mItems[position] is AdvisoryInfo) {
            return advisoryInfo
        }
        if (mItems[position] is FaqInfo) {
            return faqInfo
        }
        return -1
    }

    inner class AdvisoryInfoItemViewHolder(val binding: InfoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(info: AdvisoryInfo, position: Int) {
            binding.infoItemTitle.text = info.title
            itemView.setOnClickListener {
                listener.navigateToInfoDetailScreen(0,position)
            }
            binding.executePendingBindings()
        }
    }

    inner class FaqInfoItemViewHolder(val binding: InfoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(info: FaqInfo, position: Int) {
            binding.infoItemTitle.text = info.title
            itemView.setOnClickListener {
                listener.navigateToInfoDetailScreen(1,position)
            }
            binding.executePendingBindings()
        }
    }

}
