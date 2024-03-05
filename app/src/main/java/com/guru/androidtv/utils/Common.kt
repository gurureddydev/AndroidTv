package com.guru.androidtv.utils

import android.content.Context

class Common {
    companion object {
        fun getWidthInPercent(context: Context, percent: Int): Int {
            val width = context.resources.displayMetrics.widthPixels
            return (width * percent) / 100
        }

        fun getHeightInPercent(context: Context, percent: Int): Int {
            val height = context.resources.displayMetrics.heightPixels ?: 0
            return (height * percent) / 100
        }
    }
}