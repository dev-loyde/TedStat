package com.devloyde.tedstat.adapters

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.devloyde.tedstat.R
import com.devloyde.tedstat.databinding.NewsCategoryListItemBinding
import com.devloyde.tedstat.listeners.NavigationListeners.NewsItemUrlNavigationListener
import com.devloyde.tedstat.models.*
import com.squareup.picasso.Picasso

class NewsCategoryAdapter(val listener: NewsItemUrlNavigationListener?) :
    RecyclerView.Adapter<NewsCategoryAdapter.NewsCategoryViewHolder>() {

    private var mItems = ArrayList<Any>()

    fun addItems(items: List<Any>) {
        mItems = items as ArrayList<Any>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsCategoryViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsCategoryListItemBinding.inflate(inflater, parent, false)
        return NewsCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsCategoryViewHolder, position: Int) {
        val context = holder.image.context
        when (val news = mItems[position]) {
            is RecommendedNews -> {

                Picasso.with(context)
                    .load(news.image)
                    .fit()
                    .placeholder(R.drawable.placeholder_logo)
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = context.getString(R.string.recommended_news_source)
                holder.itemView.setOnClickListener {
                    news.link?.let { link ->
                        listener?.launchNewsUrl(link)
                    }
                }
            }
            is LocalNews -> {

                Picasso.with(context)
                    .load(news.image)
                    .fit()
                    .placeholder(R.drawable.placeholder_logo)
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = context.getString(R.string.health_care_news_source)
                holder.itemView.setOnClickListener {
                    news.link?.let { link -> listener?.launchNewsUrl(link) }
                }
            }
            is GlobalNews -> {

                Picasso.with(context)
                    .load(news.image)
                    .fit()
                    .placeholder(R.drawable.placeholder_logo)
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = context.getString(R.string.news_item_source_text, news.provider)
                holder.itemView.setOnClickListener {
                    news.link?.let { link -> listener?.launchNewsUrl(link) }
                }
            }
            is CountryNews -> {
                Picasso.with(context)
                    .load(news.image)
                    .fit()
                    .placeholder(R.drawable.placeholder_logo)
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = context.getString(R.string.news_item_source_text, news.provider)
                holder.itemView.setOnClickListener {
                    news.link?.let { link -> listener?.launchNewsUrl(link) }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mItems.size
    }


    inner class NewsCategoryViewHolder(val binding: NewsCategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var image: ImageView
        var title: TextView
        var publisher: TextView

        init {
            binding.apply {
                image = newsImg
                title = newsTitle
                publisher = newsPublisher
            }
        }


    }


}
