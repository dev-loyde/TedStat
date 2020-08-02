package com.devloyde.healthguard.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.HomeAdapter
import com.devloyde.healthguard.adapters.VerticalAdapter
import com.devloyde.healthguard.databinding.FragmentPreventionDetailBinding
import com.devloyde.healthguard.models.PreventionDetailCard
import com.devloyde.healthguard.models.VerticalRv

private const val POSITION_PARAM = "list-position"

class PreventionDetailFragment : Fragment() {
    private var listPosition: Int? = null
    private lateinit var binding: FragmentPreventionDetailBinding
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager2
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listPosition = it.getInt(POSITION_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_prevention_detail, container, false)
        bindViews()
        initPreventionPager()
        navController = findNavController()
        toolbar.setupWithNavController(navController)
        goToPagerPosition(listPosition)
        return binding.root
    }

    private fun bindViews() {
        binding.apply {
            toolbar = preventionToolbar
            viewPager = preventionSliderPager
        }
    }

    private fun initPreventionPager() {
        val safetyTipsList = listOf(
            PreventionDetailCard(
                title = "WEAR A FACE MASK",
                description = "The wearing of face masks is recommended in order to prevent those" +
                        " who are infected from but asymptomatic from spreading the virus and others " +
                        "from contacting.Properly dispose of the mask in waste bins. Improper handling" +
                        " could lead to infections",
                image = R.drawable.cover_your_nose
            ),
            PreventionDetailCard(
                title = "DONT TOUCH EYES NOSE OR MOUTH WITH UNWASHED HANDS",
                description = "Avoid touching your eyes nose and mouth with unwashed" +
                        " hands <br> Normal practices like greeting one another" +
                        " with handshakes or hugging should be avoided",
                image = R.drawable.touch_safety
            ),
            PreventionDetailCard(
                title = "WASH YOUR HANDS FREQUENTLY",
                description = "Regularly and thoroughly wash your " +
                        "hands with soap and running water or use of alcohol-based" +
                        " sanitizers if water is not available",
                image = R.drawable.wash_hands_safety_tip
            ), PreventionDetailCard(
                title = "COUGH ETIQUETTE Cover your Mouth With Sleeve Or Elbow",
                description = "If someone is coughing or sneezing, " +
                        "prompt them to cover their cough with a disposable tissue and " +
                        "discard in a waste bin and washing their hands or using their elbow bent",
                image = R.drawable.sneezing_safety
            ),
            PreventionDetailCard(
                title = "CLEAN AND DISINFECT",
                description = "If someone is coughing or sneezing, " +
                        "prompt them to cover their cough with a disposable tissue and " +
                        "discard in a waste bin and washing their hands or using their elbow bent",
                image = R.drawable.disinfectant
            ),
            PreventionDetailCard(
                title = "AVOID CONTACT WITH SICK PEOPLE",
                description = "If someone is coughing or sneezing, " +
                        "prompt them to cover their cough with a disposable tissue and " +
                        "discard in a waste bin and washing their hands or using their elbow bent",
                image = R.drawable.avoid_contact_safety
            )
        ) as List<Any>
        val adapter = VerticalAdapter(safetyTipsList, null)
        viewPager.adapter = adapter

    }

    private fun goToPagerPosition(position: Int?) {
        if (position != null) {
            this.viewPager.setCurrentItem(position,false)
        }
    }

    companion object {

        fun bundleArgs(position: Int): Bundle {
            return Bundle().apply {
                this.putInt(POSITION_PARAM, position)
            }
        }

    }


}
