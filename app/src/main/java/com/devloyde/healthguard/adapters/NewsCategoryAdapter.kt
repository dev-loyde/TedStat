package com.devloyde.healthguard.adapters

import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.observe

import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.R
import com.devloyde.healthguard.databinding.NewsCategoryListItemBinding
import com.devloyde.healthguard.models.*
import com.devloyde.healthguard.ui.news.NewsFragment
import com.squareup.picasso.Picasso


class NewsCategoryAdapter :
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
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = "source: WHO"
            }
            is LocalNews -> {

                Picasso.with(context)
                    .load(news.image)
                    .fit()
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = "source: NCDC"
            }
            is GlobalNews -> {

                Picasso.with(context)
                    .load(news.image)
                    .fit()
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = context.getString(R.string.news_item_source_text, news.provider)

            }
            is CountryNews -> {
                Picasso.with(context)
                    .load(news.image)
                    .fit()
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = context.getString(R.string.news_item_source_text, news.provider)

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
