package com.devloyde.healthguard.adapters

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding

import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.databinding.BannerContainerBinding
import com.devloyde.healthguard.databinding.PreventionDetailsItemBinding
import com.devloyde.healthguard.databinding.VerticalRvContainerBinding

import com.devloyde.healthguard.databinding.VerticalSingleItemBinding
import com.devloyde.healthguard.listeners.HomeDetailNavigationListener
import com.devloyde.healthguard.models.*
import com.squareup.picasso.Picasso


class VerticalAdapter(
    private var mItems: List<Any>,
    private var listener: HomeDetailNavigationListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val homeCardVertical: Int = 1
    private val preventionCardDetail: Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        lateinit var holder: RecyclerView.ViewHolder
        val binding: ViewDataBinding

        when (viewType) {
            homeCardVertical -> {
                binding = VerticalSingleItemBinding.inflate(inflater, parent, false)
                holder = HomeCardVerticalViewHolder(binding)
            }
            preventionCardDetail -> {
                binding = PreventionDetailsItemBinding.inflate(inflater, parent, false)
                holder = PreventionCardDetailViewHolder(binding)

            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            homeCardVertical -> homeCardView(holder as HomeCardVerticalViewHolder, position)
            preventionCardDetail -> preventionDetailCardView(
                holder as PreventionCardDetailViewHolder,
                position
            )
        }
    }


    private fun homeCardView(holder: HomeCardVerticalViewHolder, position: Int) {
        val verticalModel: HealthCard = mItems[position] as HealthCard

        holder.bind(verticalModel, position)
    }

    private fun preventionDetailCardView(holder: PreventionCardDetailViewHolder, position: Int) {
        val verticalModel: PreventionDetailCard = mItems[position] as PreventionDetailCard
        holder.bind(verticalModel, position)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {

        if (mItems[position] is HealthCard) {
            return homeCardVertical
        }
        if (mItems[position] is PreventionDetailCard) {
            return preventionCardDetail
        }
        return -1
    }


    inner class HomeCardVerticalViewHolder(val binding: VerticalSingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HealthCard, position: Int) {
            binding.item = item
            binding.executePendingBindings()
            binding.healthCard.setOnClickListener {
                listener?.navigateToPreventionDetailScreen(position)
            }
        }
    }

    inner class PreventionCardDetailViewHolder(val binding: PreventionDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PreventionDetailCard, position: Int) {
            binding.preventionTitle.text = item.title
            binding.preventionDescription.text = item.description
            Picasso.with(binding.preventionTitle.context)
                .load(item.image)
                .into(binding.preventionImg)
            binding.executePendingBindings()

        }


    }


}
