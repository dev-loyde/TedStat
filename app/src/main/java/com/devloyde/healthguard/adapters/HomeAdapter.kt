package com.devloyde.healthguard.adapters

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.devloyde.healthguard.databinding.*
import com.devloyde.healthguard.models.*
import com.squareup.picasso.Picasso


class HomeAdapter(private var mItems: ArrayList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val vertical: Int = 1
    private val horizontal: Int = 2
    private val banner: Int = 3
    private val globalStat: Int = 4


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val holder: RecyclerView.ViewHolder
        val binding: ViewDataBinding

        when (viewType) {
            banner -> {
                binding = BannerContainerBinding.inflate(inflater, parent, false)
                holder = BannerViewHolder(binding)
            }
            vertical -> {
                binding = VerticalRvContainerBinding.inflate(inflater, parent, false)
                holder = VerticalViewHolder(binding)

            }
            globalStat -> {
                binding = HomeGlobalStatBinding.inflate(inflater, parent, false)
                holder = GlobalStatViewHolder(binding)

            }
            horizontal -> {
                binding = HorizontalSingleItemBinding.inflate(inflater, parent, false)
                holder = HorizontalViewHolder(binding)
            }

            else -> {
                binding = HorizontalSingleItemBinding.inflate(inflater, parent, false)
                holder = HorizontalViewHolder(binding)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            banner -> bannerView(holder as BannerViewHolder, position)
            vertical -> verticalView(holder as VerticalViewHolder, position)
            horizontal -> horizontalView(holder as HorizontalViewHolder, position)
globalStat -> globalStatView(holder as GlobalStatViewHolder,position)
        }
    }


    private fun verticalView(holder: VerticalViewHolder, position: Int) {
        val verticalItems = mItems[position] as VerticalRv
        holder.bind(verticalItems, position)
    }

    private fun horizontalView(holder: HorizontalViewHolder, position: Int) {
        val horizontalItems = mItems[position] as HorizontalSingle
        holder.bind(horizontalItems)
    }

    private fun bannerView(holder: BannerViewHolder, position: Int) {
        val bannerItems = mItems[position] as Carousels
        holder.bind(bannerItems)

    }

    private fun globalStatView(holder: GlobalStatViewHolder, position: Int) {
        val globalStatItems = mItems[position] as GlobalStat
        holder.bind(globalStatItems)

    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {

        if (mItems[position] is VerticalRv) {
            return vertical
        }
        if (mItems[position] is HorizontalSingle) {
            return horizontal
        }
        if (mItems[position] is Carousels) {
            return banner
        }
        if (mItems[position] is GlobalStat) {
            return globalStat
        }
        return -1
    }

    class HorizontalViewHolder(private val binding: HorizontalSingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: HorizontalSingle) {
            val horizontalItem: HorizontalSingle = items
            binding.horizontalTitle.text = items.title
            binding.horizontalImg.imageAssetsFolder = "images"
            binding.horizontalImg.setAnimation(items.image)

        }
    }

    class BannerViewHolder(val binding: BannerContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: Carousels) {
            val sliderPagerAdapter = SliderPagerAdapter(items.carousels)
            binding.sliderPager.adapter = sliderPagerAdapter

            binding.pageIndicator.count = items.carousels.size
            binding.sliderPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    binding.pageIndicator.selection = position
                }

            })
        }
    }


    class VerticalViewHolder(private var binding: VerticalRvContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: VerticalRv, position: Int) {
            val verticalHeader: String = items.title
            val verticalItems: List<HealthCard> = items.verticalItems
            val adapter1 = VerticalAdapter(verticalItems)
            val mContext = binding.verticalRvContainer.context

            binding.verticalRvContainer.layoutManager =
                LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
            binding.verticalRvTitle.text = verticalHeader

            binding.verticalRvMoreBtn.setOnClickListener {
                Toast.makeText(
                    mContext,
                    "Hello this is a Vertical recyclerview at $position",
                    Toast.LENGTH_LONG
                ).show()
            }

            binding.verticalRvContainer.adapter = adapter1
        }
    }

    class GlobalStatViewHolder(private var binding: HomeGlobalStatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: GlobalStat) {

        }
    }

}