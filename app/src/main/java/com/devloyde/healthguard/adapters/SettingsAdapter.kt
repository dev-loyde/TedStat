package com.devloyde.healthguard.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.devloyde.healthguard.R
import com.devloyde.healthguard.databinding.FragmentSettingsItemBinding
import com.devloyde.healthguard.models.SettingsListItem


import com.devloyde.healthguard.ui.settings.SettingsFragment.OnListFragmentInteractionListener
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.fragment_settings_item.view.*


/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class SettingsAdapter(
    private val mItems: List<SettingsListItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

   private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as SettingsListItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentSettingsItemBinding.inflate(inflater,parent,false)
        return SettingsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val item = mItems[position]
        holder.mIconView.setImageResource(item.icon)
        holder.mTitleView.text = item.title
        if(item.pref === null){
            holder.mPref.visibility = View.INVISIBLE
        }else{
            holder.mPref.visibility = View.VISIBLE
            holder.mPref.isChecked = item.pref == 1
//            holder.mPref.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener(
//
//            ))
        }

        with(holder.mItemView) {
            tag = item
        //    setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mItems.size

    inner class SettingsViewHolder(val mItemView: View) : RecyclerView.ViewHolder(mItemView) {
        val mIconView: ImageView = mItemView.settings_icon
        val mTitleView: TextView = mItemView.settings_title
        val mPref: SwitchMaterial = mItemView.settings_pref


    }
}
