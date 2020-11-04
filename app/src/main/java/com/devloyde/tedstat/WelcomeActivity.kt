package com.devloyde.tedstat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.devloyde.tedstat.databinding.ActivityWelcomeBinding
import com.devloyde.tedstat.db.SharedPref

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    lateinit var pager: ViewPager2
    lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        val navController = findNavController(R.id.welcome_host_fragment)
        val sharedPref: SharedPref = SharedPref(this)

        if (sharedPref.loadLaunchState()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}



