package com.practice.movidb.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("loadLargeImage")
    fun loadLargeImageUrl(view: ImageView, url: String) {
        if (url.isEmpty()) return
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.ic_image_placeholder)
            .fallback(R.drawable.ic_image_not_found)
            .error(R.drawable.ic_image_not_found)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .centerCrop()
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