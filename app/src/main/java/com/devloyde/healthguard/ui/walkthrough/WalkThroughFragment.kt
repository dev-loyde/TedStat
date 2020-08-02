package com.devloyde.healthguard.ui.walkthrough

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.devloyde.healthguard.MainActivity
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.SliderPagerAdapter
import com.devloyde.healthguard.databinding.FragmentHomeBinding
import com.devloyde.healthguard.databinding.FragmentWalkThroughBinding
import com.devloyde.healthguard.models.Welcome
import com.devloyde.healthguard.models.Welcomes

class WalkThroughFragment : Fragment() {

    private lateinit var binding: FragmentWalkThroughBinding
    private lateinit var navController: NavController



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_walk_through, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initWalkThrough(mockData())
        binding.skipButton.setOnClickListener { _ ->
            gotoHomeScreen()
        }
        binding.nextButton.setOnClickListener{
            gotoNextScreen()
        }
    }

    private fun initWalkThrough(items: Welcomes) {
        val sliderPagerAdapter = SliderPagerAdapter(items.welcomes)
        binding.welcomePager.adapter = sliderPagerAdapter

        binding.welcomeIndicator.count = items.welcomes.size
        binding.welcomePager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                binding.welcomeIndicator.selection = position
            }

        })
    }

    private fun mockData(): Welcomes {

        return Welcomes(
            listOf(
                Welcome(
                    R.raw.stay_safe_stay_home,
                    "Stay Home",
                    "Help reduce spread by keeping indoors..."
                ),
                Welcome(
                    R.raw.social_distancing,
                    "Maintain suitable distances",
                    "separate yourselves from groups in suitable distances..."
                ),
                Welcome(
                    R.raw.wash_your_hands_covid_19,
                    "Wash your Hands Regularly",
                    "constant hand washing helps kill germs..."
                ),
                Welcome(
                    R.raw.dr_consultation,
                    "Seek Medical Advice",
                    "Notice unusual symptoms? seek medical advice and isolate yourself..."
                )
            )
        )

    }

    private fun gotoHomeScreen(){
        val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
    }

    private fun gotoNextScreen(){
        val currPosition: Int = binding.welcomePager.currentItem
        binding.welcomePager.setCurrentItem(currPosition + 1,true)
    }
}