package com.devloyde.healthguard.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.devloyde.healthguard.R
import com.devloyde.healthguard.databinding.CreditsItemBinding
import com.devloyde.healthguard.databinding.FragmentCreditsBinding
import com.devloyde.healthguard.models.Credits

class CreditsAdapter(
    private val values: List<Credits>
) : RecyclerView.Adapter<CreditsAdapter.CreditsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder {
        val binding = CreditsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CreditsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CreditsViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class CreditsViewHolder(val binding:CreditsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item:Credits){
            binding.categories.text = item.categories
            binding.source.text = item.source
            binding.source.setOnClickListener {

            }
        }
    }
}