package com.practice.movidb.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.practice.movidb.R
import kotlin.random.Random

object BaseBindingAdapter {

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun loadImageUrl(view: ImageView, url: String) {
        if (url.isEmpty()) return
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(view)
    }
}