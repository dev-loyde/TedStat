package com.devloyde.tedstat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.ActivityNavigator
import com.devloyde.tedstat.databinding.ActivityWelcomeBinding
import com.devloyde.tedstat.db.SharedPref

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        val sharedPref = SharedPref(this)

        if (sharedPref.loadLaunchState()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            ActivityNavigator.applyPopAnimationsToPendingTransition(this)
        }

    }

}



