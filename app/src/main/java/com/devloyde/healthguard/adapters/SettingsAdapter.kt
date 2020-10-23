package com.devloyde.healthguard.adapters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.devloyde.healthguard.R
import com.devloyde.healthguard.databinding.FragmentSettingsItemBinding
import com.devloyde.healthguard.listeners.NavigationListeners
import com.devloyde.healthguard.models.SettingsListItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.fragment_settings_item.view.*

class SettingsAdapter(
    private val mItems: List<SettingsListItem>,
    private val mListener: NavigationListeners.SettingsNavigationListener?
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    private val shareAppPosition: Int = 0
    private val openSourceLicensesPosition: Int = 1
    private val aboutUsPosition: Int = 2
    private val darkModePosition: Int = 3
    private val helpPosition: Int = 4
    private val rateUsPosition: Int = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentSettingsItemBinding.inflate(inflater, parent, false)
        return SettingsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val item = mItems[position]
        val context: Context = holder.mIconView.context
        holder.mIconView.setImageResource(item.icon)
        holder.mTitleView.text = item.title
        if (item.pref === null) {
            holder.mPref.visibility = View.INVISIBLE
        } else {
            holder.mPref.visibility = View.VISIBLE
            holder.mPref.isChecked = item.pref == 1
//            holder.mPref.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener(
//
//            ))
        }

        with(holder.mItemView) {
            tag = item
        }

        holder.itemView.setOnClickListener {
            when (position) {
                shareAppPosition -> {

                }
                openSourceLicensesPosition -> {
                    mListener?.launchOpenSourceLicenses()
                }
                aboutUsPosition -> {

                }
                darkModePosition -> {

                }
                helpPosition -> {

                }
                rateUsPosition -> {

                }
            }
        }
    }

    override fun getItemCount(): Int = mItems.size

    inner class SettingsViewHolder(val mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val mIconView: ImageView = mItemView.settings_icon
        val mTitleView: TextView = mItemView.settings_title
        val mPref: SwitchMaterial = mItemView.settings_pref

    }
}
