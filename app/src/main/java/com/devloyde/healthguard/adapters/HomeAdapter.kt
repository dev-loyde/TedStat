package com.devloyde.healthguard.adapters

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.RenderMode
import com.devloyde.healthguard.databinding.*
import com.devloyde.healthguard.listeners.NavigationListeners.HomeDetailNavigationListener

import com.devloyde.healthguard.models.*
import com.squareup.picasso.Picasso


class HomeAdapter(private var listener: HomeDetailNavigationListener?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItems = arrayListOf<Any>()
    private val vertical: Int = 1
    private val horizontal: Int = 2
    private val banner: Int = 3
    private val globalStat: Int = 4
    private val horizontalBanner: Int = 5
    private val advisoryFaq: Int = 6


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
            horizontalBanner -> {
                binding = HorizontalBannerItemBinding.inflate(inflater, parent, false)
                holder = HorizontalBannerViewHolder(binding)
            }
            globalStat -> {
                binding = HomeGlobalStatBinding.inflate(inflater, parent, false)
                holder = GlobalStatViewHolder(binding)
            }
            horizontal -> {
                binding = HorizontalSingleItemBinding.inflate(inflater, parent, false)
                holder = HorizontalViewHolder(binding)
            }
            advisoryFaq -> {
                binding = InfoRvContainerBinding.inflate(inflater, parent, false)
                holder = InfoViewHolder(binding)
            }

            else -> {
                binding = InfoRvContainerBinding.inflate(inflater, parent, false)
                holder = InfoViewHolder(binding)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            banner -> bannerView(holder as BannerViewHolder, position)
            vertical -> verticalView(holder as VerticalViewHolder, position)
            horizontal -> horizontalView(holder as HorizontalViewHolder, position)
            horizontalBanner -> horizontalBannerView(holder as HorizontalBannerViewHolder, position)
            globalStat -> globalStatView(holder as GlobalStatViewHolder, position)
            advisoryFaq -> infoView(holder as InfoViewHolder, position)
        }
    }

    fun addItem(items: ArrayList<Any>) {
            mItems = items
            notifyDataSetChanged()
    }

    private fun verticalView(holder: VerticalViewHolder, position: Int) {
        val verticalItems = mItems[position] as VerticalRv
        holder.bind(verticalItems, position, listener)
    }

    private fun horizontalView(holder: HorizontalViewHolder, position: Int) {
        val horizontalItems = mItems[position] as HorizontalSingle
        holder.bind(horizontalItems, listener)
    }

    private fun horizontalBannerView(holder: HorizontalBannerViewHolder, position: Int) {
        val horizontalBannerItems = mItems[position] as HorizontalBanner
        holder.bind(horizontalBannerItems, listener)
    }

    private fun bannerView(holder: BannerViewHolder, position: Int) {
        val bannerItems = mItems[position] as Carousels
        holder.bind(bannerItems)

    }

    private fun globalStatView(holder: GlobalStatViewHolder, position: Int) {
        val globalStatItems = mItems[position] as GlobalStat
        holder.bind(globalStatItems)

    }

    private fun infoView(holder: InfoViewHolder, position: Int) {
        val infoItems = mItems[position] as InfoRv
        holder.bind(infoItems)

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
        if (mItems[position] is HorizontalBanner) {
            return horizontalBanner
        }
        if (mItems[position] is InfoRv) {
            return advisoryFaq
        }
        return -1
    }

    inner class HorizontalViewHolder(private val binding: HorizontalSingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: HorizontalSingle, listener: HomeDetailNavigationListener?) {
            binding.horizontalTitle.text = items.title
            binding.horizontalImg.imageAssetsFolder = "images"
            binding.horizontalImg.setRenderMode(RenderMode.SOFTWARE)
            binding.horizontalImg.setAnimation(items.image)
            binding.horizontalBtn.setOnClickListener {
                listener?.launchCustomUrl(items.link!!)
            }
            binding.executePendingBindings()
        }
    }

    inner class HorizontalBannerViewHolder(private val binding: HorizontalBannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: HorizontalBanner, listener: HomeDetailNavigationListener?) {
            binding.horizontalBannerTitle.text = items.title
            Picasso.with(binding.horizontalBannerTitle.context)
                .load(items.image)
                .into(binding.horizontalBannerImg)

            binding.horizontalBannerMoreBtn.setOnClickListener {
                listener?.launchCustomUrl(items.link!!)
            }
            binding.executePendingBindings()
        }
    }

    inner class BannerViewHolder(val binding: BannerContainerBinding) :
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
            binding.executePendingBindings()
        }
    }

    inner class VerticalViewHolder(private var binding: VerticalRvContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: VerticalRv, position: Int, listener: HomeDetailNavigationListener?) {
            val verticalHeader: String = items.title
            val verticalItems: List<Any> = items.verticalItems
            val mContext = binding.verticalRvContainer.context
            val adapter1 = VerticalAdapter(verticalItems, listener)

            binding.verticalRvContainer.layoutManager =
                LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
            binding.verticalRvTitle.text = verticalHeader

            binding.verticalRvMoreBtn.setOnClickListener {
                listener?.navigateToPreventionDetailScreen()
            }

            binding.verticalRvContainer.adapter = adapter1
            binding.executePendingBindings()
        }
    }

    inner class GlobalStatViewHolder(private var binding: HomeGlobalStatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: GlobalStat) {
            binding.globalCasesValue.text = items.cases
            binding.recoveredCasesValue.text = items.recovered
            binding.deathsCasesValue.text = items.deaths

            binding.globalCasesProgress.progress = items.casesProgress!!
            binding.globalRecoveredProgress.progress = items.recoveredProgress!!
            binding.globalDeathsProgress.progress = items.deathsProgress!!

            binding.executePendingBindings()
        }
    }

    inner class InfoViewHolder(private var binding: InfoRvContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: InfoRv) {
            binding.infoRvTitle.text = items.title
            val infoItems: List<String> = items.infoItems.subList(0, 3)
            val infoAdapter = InfoAdapter(infoItems)
            binding.infoRvContainer.layoutManager =
                LinearLayoutManager(binding.infoRvContainer.context, RecyclerView.VERTICAL, false)
            binding.infoRvContainer.adapter = infoAdapter
            binding.executePendingBindings()
        }
    }
}