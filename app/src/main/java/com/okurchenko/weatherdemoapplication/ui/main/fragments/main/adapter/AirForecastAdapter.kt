package com.okurchenko.weatherdemoapplication.ui.main.fragments.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.okurchenko.weatherdemoapplication.R
import com.okurchenko.weatherdemoapplication.repositories.db.model.CurrentAirQuality
import com.okurchenko.weatherdemoapplication.utils.getAqiIndex
import com.okurchenko.weatherdemoapplication.utils.transformedDateTime

class AirForecastAdapter : BaseAdapter<CurrentAirQuality, BaseAdapter.BaseItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_air_forecast, parent, false)
        return AirItemHolder(
            view
        )
    }

    class AirItemHolder(view: View) : BaseItemHolder(view) {
        private val dateTime = view.findViewById<TextView>(R.id.itemDateTime)
        private val statusImage = view.findViewById<ImageView>(R.id.itemImage)
        private val aqi = view.findViewById<TextView>(R.id.itemAqi)

        override fun bindData(baseItem: Any) {
            val airQuality = (baseItem as CurrentAirQuality)
            dateTime.text = airQuality.ts.transformedDateTime(getDeviceLocale())
            statusImage.setImageResource(getImageStatus(airQuality.aqi))
            aqi.text = transformedAqi(airQuality.aqi)
        }

        private fun transformedAqi(aqi: Double): String {
            val aqiStatus = itemView.context.resources.getStringArray(R.array.aqi_labels)[getAqiIndex(aqi)]
            return "$aqiStatus [$aqi]"
        }

        private fun getImageStatus(aqi: Double): Int {
            val index = getAqiIndex(aqi)
            return when {
                index <= 1 -> R.drawable.ic_thumb_up_black_24dp
                index <= 3 -> R.drawable.ic_thumbs_up_down_black_24dp
                else -> R.drawable.ic_thumb_down_black_24dp
            }
        }
    }
}