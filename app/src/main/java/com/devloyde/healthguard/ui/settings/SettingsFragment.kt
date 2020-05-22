package com.devloyde.healthguard.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.SettingsAdapter
import com.devloyde.healthguard.databinding.FragmentNewsBinding
import com.devloyde.healthguard.databinding.FragmentSettingsItemListBinding
import com.devloyde.healthguard.models.SettingsListItem
import com.rd.draw.data.Orientation

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [SettingsFragment.OnListFragmentInteractionListener] interface.
 */
class SettingsFragment : Fragment() {


    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var binding: FragmentSettingsItemListBinding
    private lateinit var list: RecyclerView

    companion object{
       lateinit var toolbar: Toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_settings_item_list, container, false)
        binding.apply{
            toolbar = settingsToolbar
            list = settingsList
        }

        list.apply{

                layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
                addItemDecoration(DividerItemDecoration(activity,RecyclerView.VERTICAL))
                adapter = SettingsAdapter(
                    listOf(
                        SettingsListItem(R.drawable.ic_share_black_24dp, "Share App", null),
                        SettingsListItem(R.drawable.ic_library, "Libraries", null),
                        SettingsListItem(R.drawable.ic_about_us, "About", null),
                        SettingsListItem(R.drawable.ic_dark_mode, "Night Mode", 1),
                        SettingsListItem(R.drawable.ic_info_black_24dp, "Help",null),
                        SettingsListItem(R.drawable.ic_star_black_24dp, "Rate Us", null)
                        )
                    , listener
                )
            }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: SettingsListItem?)
    }

}
