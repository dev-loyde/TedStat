package com.devloyde.healthguard.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: Int){
        Picasso.with(view.context)
            .load(imageUrl)
            .fit()
            .into(view)
    }

