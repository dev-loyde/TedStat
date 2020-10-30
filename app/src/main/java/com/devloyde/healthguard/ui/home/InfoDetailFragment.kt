package com.devloyde.healthguard.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.ExpansionLayoutAdapter
import com.devloyde.healthguard.databinding.FragmentInfoDetailBinding
import com.devloyde.healthguard.models.AdvisoryInfo
import com.devloyde.healthguard.models.FaqInfo
import com.devloyde.healthguard.models.InfoRv
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import kotlinx.android.synthetic.main.fragment_info_detail.*

// the fragment info type initialization parameters
private lateinit var homeViewModel: HomeViewModel
private const val ADVISORY_INFO = 0
private const val FAQ_INFO = 1

private const val INFO_TYPE = "info-type"
private const val INFO_TYPE_POSITION = "info-type-position"

class InfoDetailFragment : Fragment() {

    private var infoTypeParam: Int? = null
    private lateinit var binding: FragmentInfoDetailBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var expansionInfoAdapter: ExpansionLayoutAdapter
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            infoTypeParam = it.getInt(INFO_TYPE)
        }

        Toast.makeText(context,infoTypeParam.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info_detail, container, false)
        bindViews()
        navController = findNavController()
        toolbar.setupWithNavController(navController)
        setUpExpandableRecyclerView()
        return binding.root
    }

    private fun bindViews() {
        binding.apply {
            recyclerView = expansionInfoLayoutRecyclerView
            toolbar = infoToolbar
        }
    }

    private fun setUpExpandableRecyclerView() {
        expansionInfoAdapter = ExpansionLayoutAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = expansionInfoAdapter
        }
        loadItems()
    }

    private fun loadItems() {
        when (infoTypeParam) {
            ADVISORY_INFO -> {
                homeViewModel.advisoryInfo.observe(viewLifecycleOwner) { advisoryInfo ->
                    // GLOBAL STATISTICS
                    if (advisoryInfo is List<AdvisoryInfo>) {
                        toolbar.title = "Advisory"
                        expansionInfoAdapter.setItems(advisoryInfo)
                    }
                }
            }
            FAQ_INFO -> {
                homeViewModel.faqInfo.observe(viewLifecycleOwner) { faqInfo ->
                    // GLOBAL STATISTICS
                    if (faqInfo is List<FaqInfo>) {
                        toolbar.title = "Faq"
                        expansionInfoAdapter.setItems(faqInfo)
                    }
                }
            }
        }


    }

    companion object {

        fun bundleArgs(infoType:Int): Bundle {
            return Bundle().apply {
                putInt(INFO_TYPE, infoType)

            }
        }
    }
}