package com.devloyde.healthguard.adapters

import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.observe

import androidx.recyclerview.widget.RecyclerView
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
        when (val news = mItems[position]) {
            is RecommendedNews -> {

                Picasso.with(holder.image.context)
                    .load(news.image)
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = "WHO"
            }
            is LocalNews -> {

                Picasso.with(holder.image.context)
                    .load(news.image)
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = "NCDC"
            }
            is GlobalNews -> {

                Picasso.with(holder.image.context)
                    .load(news.image)
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = news.provider

            }
            is CountryNews -> {
                Picasso.with(holder.image.context)
                    .load(news.image)
                    .into(holder.image)

                holder.title.text = news.title
                holder.publisher.text = news.provider

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
