package com.devloyde.healthguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.databinding.ExpansionPanelRecyclerCellItemBinding
import com.devloyde.healthguard.databinding.LoadingPlaceholderViewBinding
import com.devloyde.healthguard.models.AdvisoryInfo
import com.devloyde.healthguard.models.FaqInfo
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection


class ExpansionLayoutAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mItems: MutableList<Any> = ArrayList()
    val expansionsCollection = ExpansionLayoutCollection()

    private val advisoryInfo: Int = 0
    private val faqInfo: Int = 1

    lateinit var binding: ViewDataBinding
    lateinit var holder: RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {
            advisoryInfo -> {
                binding = ExpansionPanelRecyclerCellItemBinding.inflate(inflater, parent, false)
                holder = AdvisoryInfoItemViewHolder(binding as ExpansionPanelRecyclerCellItemBinding)
            }
            faqInfo -> {
                binding = ExpansionPanelRecyclerCellItemBinding.inflate(inflater, parent, false)
                holder = FaqInfoItemViewHolder(binding as ExpansionPanelRecyclerCellItemBinding)
            }
            else -> {
                binding = LoadingPlaceholderViewBinding.inflate(inflater, parent, false)
                holder = HomeAdapter.LoadingViewHolder(binding as LoadingPlaceholderViewBinding)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            advisoryInfo -> {
                val info = mItems[position] as AdvisoryInfo
                val advisoryInfoViewHolder = holder as AdvisoryInfoItemViewHolder
                advisoryInfoViewHolder.bind(info)
                expansionsCollection.add(holder.expansionLayout)
            }
            faqInfo -> {
                val info = mItems[position] as FaqInfo
                val faqInfoViewHolder = holder as FaqInfoItemViewHolder
                faqInfoViewHolder.bind(info)
                expansionsCollection.add(holder.expansionLayout)
            }
        }
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

    fun setItemExpanded(position:Int){
        if (mItems[position] is AdvisoryInfo) {
            val advisoryInfoViewHolder = holder as AdvisoryInfoItemViewHolder
            advisoryInfoViewHolder.expansionLayout.isExpanded
        }
        if (mItems[position] is FaqInfo) {
            val faqInfoViewHolder = holder as FaqInfoItemViewHolder
            faqInfoViewHolder.expansionLayout.isExpanded
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun setItems(items: List<Any>) {
        mItems.addAll(items)
        notifyDataSetChanged()
    }

    init {
        expansionsCollection.openOnlyOne(true)
    }

    inner class AdvisoryInfoItemViewHolder(val binding: ExpansionPanelRecyclerCellItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var expansionLayout: ExpansionLayout = binding.expansionLayout

        fun bind(info: AdvisoryInfo) {
            binding.infoTitle.text = info.title
            binding.infoContent.text = info.description
            binding.expansionLayout.collapse(true)
            binding.executePendingBindings()
        }
    }

    inner class FaqInfoItemViewHolder(val binding: ExpansionPanelRecyclerCellItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var expansionLayout: ExpansionLayout = binding.expansionLayout

        fun bind(info: FaqInfo) {
            binding.infoTitle.text = info.title
            binding.infoContent.text = info.description
            binding.expansionLayout.collapse(true)

            binding.executePendingBindings()
        }
    }
}