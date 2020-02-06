package com.okurchenko.weatherdemoapplication.ui.main.fragments.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.okurchenko.weatherdemoapplication.R
import com.okurchenko.weatherdemoapplication.repositories.db.model.WeatherForecast
import com.okurchenko.weatherdemoapplication.utils.GlideApp
import com.okurchenko.weatherdemoapplication.utils.addCelsiusToTemp
import com.okurchenko.weatherdemoapplication.utils.transformedDateTime

class WeatherForecastAdapter : BaseAdapter<WeatherForecast, BaseAdapter.BaseItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_weather_forecast, parent, false)
        return ForecastItemHolder(
            view
        )
    }

    class ForecastItemHolder(view: View) : BaseItemHolder(view) {
        private val weatherIcon = view.findViewById<ImageView>(R.id.weatherIcon)
        private val weatherMinTempLabel = view.findViewById<TextView>(R.id.weatherMinTempLabel)
        private val weatherTempLabel = view.findViewById<TextView>(R.id.weatherTempLabel)
        private val weatherMaxTempLabel = view.findViewById<TextView>(R.id.weatherMaxTempLabel)
        private val dateTimeLabel = view.findViewById<TextView>(R.id.dateTime)

        override fun bindData(baseItem: Any) {
            val forecast = baseItem as WeatherForecast
            weatherMinTempLabel.text =
                itemView.resources.getString(R.string.min_temp_label, addCelsiusToTemp(forecast.minTemp))
            weatherTempLabel.text = addCelsiusToTemp(forecast.temp)
            weatherMaxTempLabel.text =
                itemView.resources.getString(R.string.max_temp_label, addCelsiusToTemp(forecast.maxTemp))
            dateTimeLabel.text = forecast.ts.toLong().transformedDateTime(getDeviceLocale())
            GlideApp.with(itemView.context)
                .load(getIconUrlFromCode(forecast.iconCode))
                .apply(RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                .placeholder(R.drawable.ic_launcher_background)
                .into(weatherIcon)

        }


        private fun getIconUrlFromCode(code: String): String = "https://www.weatherbit.io/static/img/icons/$code.png"
    }
}