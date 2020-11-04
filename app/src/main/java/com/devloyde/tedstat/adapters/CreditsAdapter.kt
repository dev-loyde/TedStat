package com.devloyde.tedstat.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.devloyde.tedstat.databinding.CreditsItemBinding
import com.devloyde.tedstat.databinding.FragmentCreditsBinding
import com.devloyde.tedstat.listeners.NavigationListeners
import com.devloyde.tedstat.models.Credits

class CreditsAdapter(
    private val values: List<Credits>,
    private val listener: NavigationListeners.SocialsNavigationListener?
) : RecyclerView.Adapter<CreditsAdapter.CreditsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder {
        val binding = CreditsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CreditsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CreditsViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class CreditsViewHolder(val binding: CreditsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Credits) {
            binding.categories.text = item.categories
            binding.source.text = item.source
            itemView.setOnClickListener {
                listener?.launchSocialFollow(item.link)
            }
        }
    }
}