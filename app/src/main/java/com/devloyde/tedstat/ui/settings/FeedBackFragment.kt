package com.devloyde.tedstat.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.devloyde.tedstat.R
import com.devloyde.tedstat.databinding.FragmentFeedBackBinding
import com.devloyde.tedstat.models.FeedBack
import com.google.android.material.textfield.TextInputLayout
import com.tapadoo.alerter.Alerter
import java.util.*


class FeedBackFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    lateinit var binding: FragmentFeedBackBinding
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var emailAddressInputLayout: TextInputLayout
    private lateinit var feedBackInput: TextInputLayout
    private lateinit var submitBtn: Button

    private var name: String? = null
    private var emailAddress: String? = null
    private var feedBackMsg: String? = null

    private lateinit var viewInflater: LayoutInflater
    private var viewContainer: ViewGroup? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewInflater = inflater
        viewContainer = container
        // Inflate the layout for this fragment
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed_back, container, false)
        navController = findNavController()
        bindViews()
        toolbar.setupWithNavController(navController)

        submitBtn.setOnClickListener {
            checkValidations()
        }

        return binding.root
    }

    private fun bindViews() {
        binding.apply {
            toolbar = feedbackToolbar
            nameInputLayout = nameInput
            emailAddressInputLayout = emailInput
            feedBackInput = feedbackMsgInput
            submitBtn = feedbackSubmitBtn
        }
    }

    private fun checkValidations() {
        name = nameInputLayout.editText?.text.toString()
        emailAddress = emailAddressInputLayout.editText?.text.toString()
        feedBackMsg = feedBackInput.editText?.text.toString()

        var validationErr = false

        if (name == "" || name == null) {
            nameInputLayout.isErrorEnabled = true
            nameInputLayout.error = "This field is required"
            validationErr = true
        } else {
            nameInputLayout.isErrorEnabled = false
        }
        if (emailAddress == "" || emailAddress == null) {
            emailAddressInputLayout.isErrorEnabled = true
            emailAddressInputLayout.error = "This field is required"
            validationErr = true
        } else {
            emailAddressInputLayout.isErrorEnabled = false
        }
        if (feedBackMsg == "" || feedBackMsg == null) {
            feedBackInput.isErrorEnabled = true
            feedBackInput.error = "This field is required"
            validationErr = true
        } else {
            feedBackInput.isErrorEnabled = false
        }

        if (!validationErr) {
            submitProject(
                FeedBack(
                    name!!,
                    emailAddress!!,
                    feedBackMsg!!,
                    Calendar.getInstance().time.toString()
                )
            )
        }

    }

    private fun submitProject(feedBack: FeedBack) {
        Alerter.create(activity)
            .setTitle("Confirm Submission")
            .setDuration(30000)
            .setText("Are you sure you want to submit...")
            .addButton("Yes", R.style.AlertButton, View.OnClickListener {
                Alerter.create(activity)
                    .setTitle("Loading")
                    .setDuration(20000)
                    .setText("Tap to dismiss...")
                    .enableProgress(true)
                    .enableSwipeToDismiss()
                    .setProgressColorRes(android.R.color.white)
                    .show()
                makeSubmission(feedBack)
            })
            .addButton("No", R.style.AlertButton, View.OnClickListener {
                Alerter.hide()
            }).show()
    }

    private fun makeSubmission(feedBack: FeedBack) {
        settingsViewModel.submitFeedBack(feedBack).observe(viewLifecycleOwner) { success ->
            when (success) {
                true -> {
                    Alerter.create(activity)
                        .setText("FeedBack sent successfully")
                        .setDuration(20000)
                        .setIcon(R.drawable.ic_check)
                        .setBackgroundColorRes(R.color.colorPrimary)
                        .show()
                }
                false -> {
                    Alerter.create(activity)
                        .setText("Feedback wasn't sent successfully")
                        .setDuration(20000)
                        .setIcon(R.drawable.ic_cancel)
                        .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                        .show()
                }
            }
        }
    }

}