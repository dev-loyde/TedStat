package com.devloyde.tedstat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.RenderMode
import com.devloyde.tedstat.databinding.CarouselItemBinding
import com.devloyde.tedstat.databinding.WelcomeItemBinding
import com.devloyde.tedstat.models.Carousel
import com.devloyde.tedstat.models.Welcome
import com.squareup.picasso.Picasso

class SliderPagerAdapter(items: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val banner: Int = 1
    private val welcome: Int = 2
    private var mItems: List<Any> = items

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val holder: RecyclerView.ViewHolder
        val binding: ViewDataBinding

        when (viewType) {
            banner -> {
                binding = CarouselItemBinding.inflate(inflater, parent, false)
                holder = CarouselItemViewHolder(binding)
            }
            welcome -> {
                binding = WelcomeItemBinding.inflate(inflater, parent, false)
                holder = WelcomeViewHolder(binding)

            }
            else -> {
                binding = CarouselItemBinding.inflate(inflater, parent, false)
                holder = CarouselItemViewHolder(binding)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            banner -> bannerView(holder as CarouselItemViewHolder, position)
            welcome -> welcomeView(holder as WelcomeViewHolder, position)
        }
    }

    private fun bannerView(holder: CarouselItemViewHolder, position: Int) {
        val horizontalItems = mItems[position] as Carousel
        holder.bind(horizontalItems)
    }

    private fun welcomeView(holder: WelcomeViewHolder, position: Int) {
        val welcomeItems = mItems[position] as Welcome
        holder.bind(welcomeItems)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {

        if (mItems[position] is Carousel) {
            return banner
        }
        if (mItems[position] is Welcome) {
            return welcome
        }
        return -1
    }


    class CarouselItemViewHolder(private val binding: CarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Carousel) {
            Picasso
                .with(binding.carouselImg.context)
                .load(item.image)
                .into(binding.carouselImg)
            binding.executePendingBindings()
        }
    }

    class WelcomeViewHolder(private val binding: WelcomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Welcome) {
            binding.item = item
            binding.welcomeImg.imageAssetsFolder = "images"
            binding.welcomeImg.setRenderMode(RenderMode.SOFTWARE)
            binding.welcomeImg.setAnimation(item.img)
            binding.executePendingBindings()
        }
    }

}