package com.devloyde.tedstat.adapters

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras

import androidx.recyclerview.widget.RecyclerView
import com.devloyde.tedstat.R
import com.devloyde.tedstat.databinding.PreventionDetailsItemBinding

import com.devloyde.tedstat.databinding.PreventionSingleItemBinding
import com.devloyde.tedstat.listeners.NavigationListeners.HomeDetailNavigationListener
import com.devloyde.tedstat.models.*
import com.devloyde.tedstat.ui.home.PreventionDetailFragment
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
                binding = PreventionSingleItemBinding.inflate(inflater, parent, false)
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


    inner class HomeCardVerticalViewHolder(val binding: PreventionSingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HealthCard, position: Int) {
            Picasso.with(binding.verticalImg.context)
                .load(item.image)
                .into(binding.verticalImg)
            binding.verticalTitle.text = item.title
            ViewCompat.setTransitionName(binding.verticalImg, item.image.toString())
            binding.healthCard.setOnClickListener {
              listener?.navigateToPreventionDetailScreen(item,position, binding.verticalImg)
            }
            binding.executePendingBindings()
        }
    }

    inner class PreventionCardDetailViewHolder(val binding: PreventionDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PreventionDetailCard, position: Int) {
            Picasso.with(binding.preventionImg.context)
                .load(item.image)
                .into(binding.preventionImg)
            binding.preventionTitle.text = item.title
            binding.preventionDescription.text = item.description
            binding.executePendingBindings()

        }


    }

}
