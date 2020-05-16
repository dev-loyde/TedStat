package com.devloyde.healthguard.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


object Utils{

//    fun NetworkAvailable(context: Context): Boolean {
//        val cm =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return if (cm != null) {
//            cm.activeNetwork != null && cm.activeNetworkInfo.isConnectedOrConnecting
//        } else false
//    }

}
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: Int){
        Picasso.with(view.context)
            .load(imageUrl)
            .fit()
            .into(view)
    }

