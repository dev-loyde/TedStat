package com.devloyde.healthguard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.devloyde.healthguard.databinding.ActivityWelcomeBinding


class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_welcome)

        val navController = findNavController(R.id.welcome_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

    }
}



//<com.google.android.material.appbar.AppBarLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:theme="@style/AppTheme.AppBarOverlay">
//
//<TextView
//android:id="@+id/title"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:gravity="center"
//android:minHeight="?actionBarSize"
//android:padding="@dimen/appbar_padding"
//android:text="@string/app_name"
//android:textAppearance="@style/TextAppearance.Widget.AppCompat.Toolbar.Title" />
//
//<com.google.android.material.tabs.TabLayout
//android:id="@+id/tabs"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:background="?attr/colorPrimary" />
//</com.google.android.material.appbar.AppBarLayout>
