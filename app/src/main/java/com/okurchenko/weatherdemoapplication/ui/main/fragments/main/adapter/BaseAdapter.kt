package com.okurchenko.weatherdemoapplication.ui.main.fragments.main.adapter

import android.os.Build
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<I : Any, V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {

    var baseItems: List<I> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: V, position: Int) {
        (holder as BaseItemHolder).bindData(baseItems[position])
    }

    override fun getItemCount(): Int = baseItems.size

    abstract class BaseItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindData(baseItem: Any)

        @Suppress("DEPRECATION")
        private val locale: Locale by lazy {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                itemView.context.resources.configuration.locales.get(0)
            } else {
                itemView.context.resources.configuration.locale
            }
        }

        fun getDeviceLocale(): Locale = locale
    }
}