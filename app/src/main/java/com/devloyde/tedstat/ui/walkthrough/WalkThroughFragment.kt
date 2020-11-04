package com.devloyde.tedstat.ui.walkthrough

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.viewpager2.widget.ViewPager2
import com.devloyde.tedstat.R
import com.devloyde.tedstat.adapters.SliderPagerAdapter
import com.devloyde.tedstat.databinding.FragmentWalkThroughBinding
import com.devloyde.tedstat.models.Welcome
import com.devloyde.tedstat.models.Welcomes
import com.rd.PageIndicatorView

class WalkThroughFragment : Fragment() {

    private lateinit var binding: FragmentWalkThroughBinding
    private lateinit var navController: NavController
    private lateinit var mViewPager: ViewPager2
    private lateinit var mIndicator: PageIndicatorView
    private lateinit var mNextButton: Button
    private lateinit var mSkipButton: Button
    private lateinit var mTransition: Transition


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_walk_through, container, false)
        bindViews()
        return binding.root
    }

    private fun bindViews() {
        binding.apply {
            mViewPager = welcomePager
            mIndicator = welcomeIndicator
            mNextButton = nextButton
            mSkipButton = skipButton
        }
        mTransition = Slide(Gravity.BOTTOM)
        mTransition.duration = 700
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initWalkThrough(loadData())
        mSkipButton.setOnClickListener {
            gotoWelcomePage()
        }
        mNextButton.setOnClickListener {
            gotoNextScreen()
        }
    }

    private fun initWalkThrough(items: Welcomes) {
        val sliderPagerAdapter = SliderPagerAdapter(items.welcomes)
        mViewPager.adapter = sliderPagerAdapter

        mIndicator.count = items.welcomes.size
        mViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                mIndicator.selection = position
                getStartedMode(position)
            }

        })
    }

    private fun loadData(): Welcomes {

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

    private fun gotoWelcomePage() {
        navController.navigate(R.id.action_walkThroughFragment_to_welcomeFragment)
    }

    private fun gotoNextScreen() {
        val currPosition: Int = mViewPager.currentItem
        mViewPager.setCurrentItem(currPosition + 1, true)
        getStartedMode(currPosition + 1)
    }

    private fun getStartedMode(position: Int) {
        if (position == loadData().welcomes.size - 1) {
            mNextButton.setOnClickListener {
                gotoWelcomePage()
            }
        }else{
            mNextButton.setOnClickListener {
                gotoNextScreen()
            }
        }
    }
}