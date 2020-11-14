package com.devloyde.tedstat.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2
import com.devloyde.tedstat.R
import com.devloyde.tedstat.adapters.VerticalAdapter
import com.devloyde.tedstat.databinding.FragmentPreventionDetailBinding
import com.devloyde.tedstat.models.HealthCard
import com.devloyde.tedstat.models.PreventionDetailCard
import com.rd.PageIndicatorView
import com.squareup.picasso.Picasso

private const val POSITION_PARAM = "list-position"
private const val PREVENTION_PARAM = "prevention-position"

class PreventionDetailFragment : Fragment() {
    private var listPosition: Int? = null
    private lateinit var preventionItem: HealthCard
    private lateinit var binding: FragmentPreventionDetailBinding
    private lateinit var toolbar: Toolbar
    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var viewPagerIndicator: PageIndicatorView
    private lateinit var viewPagerControls: ConstraintLayout

    private lateinit var viewPager: ViewPager2
    private lateinit var navController: NavController

    private lateinit var previousBtn: Button
    private lateinit var nextBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listPosition = it.getInt(POSITION_PARAM)
            preventionItem = it.getParcelable(PREVENTION_PARAM)!!
        }
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_prevention_detail, container, false)
        bindViews()

        initPreventionPager(listPosition)
        navController = findNavController()
        toolbar.setupWithNavController(navController)

        return binding.root
    }

    private fun bindViews() {
        binding.apply {
            toolbar = preventionToolbar
            image = preventionCard.preventionImg
            title = preventionCard.preventionTitle
            description = preventionCard.preventionDescription
            viewPager = preventionSliderPager
            viewPagerIndicator = preventionPagerIndicator
            viewPagerControls = preventionPagerControls
            previousBtn = prevButton
            nextBtn = nextButton
        }
    }

    private fun initPreventionPager(position: Int?) {
        if (position == null) {
            val safetyTipsList = listOf(
                PreventionDetailCard(
                    title = "WEAR A FACE MASK",
                    description = "The wearing of face masks is recommended in order to prevent those" +
                            " who are infected from but asymptomatic from spreading the virus and others " +
                            "from contacting.Properly dispose of the mask in waste bins. Improper handling" +
                            " could lead to infections.",
                    image = R.drawable.cover_your_nose
                ),
                PreventionDetailCard(
                    title = "DONT TOUCH EYES NOSE OR MOUTH WITH UNWASHED HANDS",
                    description = "Avoid touching your eyes nose and mouth with unwashed" +
                            " hands Normal practices like greeting one another" +
                            " with handshakes or hugging should be avoided.",
                    image = R.drawable.touch_safety
                ),
                PreventionDetailCard(
                    title = "WASH YOUR HANDS FREQUENTLY",
                    description = "Regularly and thoroughly wash your " +
                            "hands with soap and running water or use of alcohol-based" +
                            " sanitizers if water is not available.",
                    image = R.drawable.wash_hands_safety_tip
                ), PreventionDetailCard(
                    title = "COUGH ETIQUETTE Cover your Mouth With Sleeve Or Elbow",
                    description = "If someone is coughing or sneezing, " +
                            "prompt them to cover their cough with a disposable tissue and " +
                            "discard in a waste bin and washing their hands or using their elbow bent.",
                    image = R.drawable.sneezing_safety
                ),
                PreventionDetailCard(
                    title = "CLEAN AND DISINFECT",
                    description = "If someone is coughing or sneezing, " +
                            "prompt them to cover their cough with a disposable tissue and " +
                            "discard in a waste bin and washing their hands or using their elbow bent.",
                    image = R.drawable.disinfectant
                ),
                PreventionDetailCard(
                    title = "AVOID CONTACT WITH SICK PEOPLE",
                    description = "If someone is coughing or sneezing, " +
                            "prompt them to cover their cough with a disposable tissue and " +
                            "discard in a waste bin and washing their hands or using their elbow bent.",
                    image = R.drawable.avoid_contact_safety
                )
            ) as List<Any>
            val adapter = VerticalAdapter(safetyTipsList, null)
            viewPager.adapter = adapter
            viewPager.visibility = View.VISIBLE
            viewPagerControls.visibility = View.VISIBLE
            viewPagerIndicator.count = safetyTipsList.size
            viewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewPagerIndicator.selection = position
                }
            })
            setUpViewpagerControls(safetyTipsList.size)
        } else {
            ViewCompat.setTransitionName(image, preventionItem.image.toString())
            Picasso.with(context)
                .load(preventionItem.image)
                .into(image)
            title.text = preventionItem.title
            description.text = preventionItem.description
            binding.preventionCard.healthCard.visibility = View.VISIBLE
        }
    }

    private fun setUpViewpagerControls(size: Int) {
        previousBtn.setOnClickListener {
            var position: Int = viewPager.currentItem
            if (position < size) {
                position--
                viewPager.setCurrentItem(position, true)
            }
            if (position < 0) {
                viewPager.setCurrentItem(size-1, true)
            }
        }
        nextBtn.setOnClickListener {
            var position: Int = viewPager.currentItem
            if (position < size) {
                position++
                viewPager.setCurrentItem(position, true)
            }
            if (position == size) {
                viewPager.setCurrentItem(0, true)
            }
        }
    }

    companion object {

        fun bundleArgs(position: Int, item: HealthCard): Bundle {
            return Bundle().apply {
                this.putInt(POSITION_PARAM, position)
                this.putParcelable(PREVENTION_PARAM, item)
            }
        }


    }

}
