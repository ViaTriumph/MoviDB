package com.practice.movidb.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.movidb.R

object BaseBindingAdapter {

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun loadImageUrl(view: ImageView, url: String) {
        if (url.isEmpty()) return
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.ic_image_placeholder)
            .fallback(R.drawable.ic_image_not_found)
            .error(R.drawable.ic_image_not_found)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("adapter")
    fun setRecyclerViewAdapter(
        view: RecyclerView,
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    ) {
        view.adapter = adapter
    }
}