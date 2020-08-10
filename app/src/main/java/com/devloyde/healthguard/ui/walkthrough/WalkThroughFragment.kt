package com.devloyde.healthguard.ui.walkthrough

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.viewpager2.widget.ViewPager2
import com.devloyde.healthguard.R
import com.devloyde.healthguard.adapters.SliderPagerAdapter
import com.devloyde.healthguard.databinding.FragmentWalkThroughBinding
import com.devloyde.healthguard.models.Welcome
import com.devloyde.healthguard.models.Welcomes
import com.rd.PageIndicatorView

class WalkThroughFragment : Fragment() {

    private lateinit var binding: FragmentWalkThroughBinding
    private lateinit var navController: NavController
    private lateinit var mViewPager: ViewPager2
    private lateinit var mIndicator: PageIndicatorView
    private lateinit var mNextButton: Button
    private lateinit var mSkipButton: Button
    private lateinit var mWelcomeButton: Button
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
            mWelcomeButton = welcomeBtn
        }
        mTransition = Slide(Gravity.BOTTOM)
        mTransition.duration = 700
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initWalkThrough(mockData())
        mSkipButton.setOnClickListener {
            gotoLastScreen()
        }
        mNextButton.setOnClickListener {
            gotoNextScreen()
        }
        mWelcomeButton.setOnClickListener {
            gotoHome()
        }
    }

    private fun initWalkThrough(items: Welcomes) {
        val sliderPagerAdapter = SliderPagerAdapter(items.welcomes)
        mViewPager.adapter = sliderPagerAdapter

        mIndicator.count = items.welcomes.size -1
        mViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                mIndicator.selection = position
                getStartedMode(position)
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
                ),
                Welcome(
                    R.raw.coronavirus,
                    getString(R.string.welcome_msg_title),
                    getString(R.string.welcome_msg)
                )
            )
        )

    }

    private fun gotoHome() {
        navController.navigate(R.id.action_walkThroughFragment_to_mainActivity)
    }

    private fun gotoLastScreen() {
        mViewPager.setCurrentItem(mockData().welcomes.size - 1, true)
    }

    private fun gotoNextScreen() {
        val currPosition: Int = mViewPager.currentItem
        mViewPager.setCurrentItem(currPosition + 1, true)
        getStartedMode(currPosition + 1)
    }

    private fun getStartedMode(position: Int) {
        if (position == mockData().welcomes.size - 1) {
            mNextButton.visibility = View.INVISIBLE
            mSkipButton.visibility = View.INVISIBLE
            mIndicator.visibility = View.INVISIBLE
            mWelcomeButton.visibility = View.VISIBLE
            mTransition.addTarget(mWelcomeButton)
        } else {
            mNextButton.visibility = View.VISIBLE
            mSkipButton.visibility = View.VISIBLE
            mIndicator.visibility = View.VISIBLE
            mWelcomeButton.visibility = View.INVISIBLE
        }
    }
}