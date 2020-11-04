package com.devloyde.tedstat.ui.walkthrough

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.devloyde.tedstat.MainActivity
import com.devloyde.tedstat.R
import com.devloyde.tedstat.databinding.FragmentWelcomeBinding
import com.devloyde.tedstat.db.SharedPref

class WelcomeFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        sharedPref = activity?.applicationContext?.let { SharedPref(it) }!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.welcomeBtn.setOnClickListener {
            sharedPref.setLaunchState(true)
            gotoHome()
        }
    }

    private fun gotoHome() {
        navController.navigate(R.id.action_welcomeFragment_to_mainActivity)
    }

}