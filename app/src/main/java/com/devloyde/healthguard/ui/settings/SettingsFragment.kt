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
import androidx.recyclerview.widget.DividerItemDecoration
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.SettingsAdapter
import com.devloyde.healthguard.models.SettingsListItem
import com.rd.draw.data.Orientation

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [SettingsFragment.OnListFragmentInteractionListener] interface.
 */
class SettingsFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
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
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: SettingsListItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
