package com.okurchenko.weatherdemoapplication.utils

import android.view.View
import kotlin.math.round

fun addCelsiusToTemp(value: Double): String = "${round(value).toInt()} \u2103"
fun addPercent(value: Double): String = "$value %"

class SnackBarBuilder(
    val text: String,
    val duration: Int,
    val actionText: String? = null,
    val actionListener: View.OnClickListener? = null,
    val icon: Int
) {
    companion object {
        const val emptyIcon = -1
    }

    class Builder {
        private lateinit var text: String
        private var duration: Int = 0
        private var actionText: String? = null
        private var actionListener: View.OnClickListener? = null
        private var icon: Int = -1

        fun setText(text: String) = apply { this.text = text }
        fun setDuration(duration: Int) = apply { this.duration = duration }
        fun setActionText(actionText: String) = apply { this.actionText = actionText }
        fun setActionListener(actionListener: View.OnClickListener) = apply { this.actionListener = actionListener }
        fun setIcon(icon: Int) = apply { this.icon = icon }

        fun build(): SnackBarBuilder = SnackBarBuilder(text, duration, actionText, actionListener, icon)
    }
}

