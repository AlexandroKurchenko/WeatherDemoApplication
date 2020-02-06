package com.okurchenko.weatherdemoapplication.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.okurchenko.weatherdemoapplication.R
import com.okurchenko.weatherdemoapplication.ui.main.fragments.main.adapter.BaseAdapter

@BindingAdapter("loadCroppedImage")
fun loadCroppedImage(view: ImageView, imageUrl: String?) {
    imageUrl?.run {
        GlideApp.with(view.context)
            .load("https://www.weatherbit.io/static/img/icons/$imageUrl.png")
            .apply(RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
            .placeholder(R.drawable.ic_launcher_background)
            .into(view)
    }
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.run {
        GlideApp.with(view.context)
            .load(imageUrl).apply(RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
            .placeholder(R.drawable.ic_launcher_background)
            .into(view)
    }
}

@BindingAdapter("setAdapterItems")
fun setRecyclerViewAdapter(recyclerView: RecyclerView, data: List<Any>?) {
    data?.run {
        (recyclerView.adapter as? BaseAdapter<Any, BaseAdapter.BaseItemHolder>)?.baseItems = data
    }
}