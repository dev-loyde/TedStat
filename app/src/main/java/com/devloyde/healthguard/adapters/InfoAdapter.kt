package com.devloyde.healthguard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.databinding.InfoListItemBinding

class InfoAdapter(
    private var mItems: List<String>
    ) : RecyclerView.Adapter<InfoAdapter.InfoItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoItemViewHolder {
            val inflater: LayoutInflater = LayoutInflater.from(parent.context)
            val binding = InfoListItemBinding.inflate(inflater,parent,false)
            return InfoItemViewHolder(binding)
        }

        override fun onBindViewHolder(holder: InfoItemViewHolder, position: Int) {
            val info: String = mItems[position]
            holder.bind(info)

        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        inner class InfoItemViewHolder(val binding: InfoListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(title: String){
                binding.infoItemTitle.text = title
                binding.executePendingBindings()
            }
        }

    }
