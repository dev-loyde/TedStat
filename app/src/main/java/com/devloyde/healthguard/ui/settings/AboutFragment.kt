package com.devloyde.healthguard.ui.settings

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.devloyde.healthguard.R
import com.devloyde.healthguard.databinding.FragmentAboutBinding
import com.devloyde.healthguard.listeners.NavigationListeners
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {

    lateinit var binding: FragmentAboutBinding
    lateinit var toolbar: Toolbar
    private lateinit var twitterFollow: TextView
    private lateinit var instagramFollow: TextView
    private lateinit var linkedInFollow: TextView
    lateinit var navController: NavController

    private lateinit var creatorAttributionText: TextView
    private lateinit var socialsNavigationListener: NavigationListeners.SocialsNavigationListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        bindViews()
        navController = findNavController()
        toolbar.setupWithNavController(navController)
        setUpCreatorAttribution()
        setUpFollowLinks()
        return binding.root
    }

    private fun bindViews() {
        binding.apply {
            toolbar = aboutToolbar
            creatorAttributionText = creatorAttribution
            twitterFollow = followTwitter
            instagramFollow = followInstagram
            linkedInFollow = followLinkedin
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context){
            is NavigationListeners.SocialsNavigationListener -> {
                socialsNavigationListener = context
            }
        }
    }
    private fun setUpCreatorAttribution() {
        val ssb = SpannableStringBuilder()
        ssb.append("made with  ")
        ssb.setSpan(
            ImageSpan(requireContext(), R.drawable.ic_love),
            ssb.length - 1,
            ssb.length,
            0
        )
        ssb.append(" by Thaddeus Oseghale ")
        creatorAttributionText.text = ssb
    }

    private fun setUpFollowLinks() {
        twitterFollow.setOnClickListener {
            socialsNavigationListener.launchSocialFollow("https://twitter.com/dev_loyde")
        }
        instagramFollow.setOnClickListener {
            socialsNavigationListener.launchSocialFollow("https://instagram.com/dev_loyde")
        }
        linkedInFollow.setOnClickListener {
            socialsNavigationListener.launchSocialFollow("https://www.linkedin.com/in/thaddeus-oseghale")
        }
    }

}