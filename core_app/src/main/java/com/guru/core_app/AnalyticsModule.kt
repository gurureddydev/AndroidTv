package com.guru.core_app

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
object AnalyticsModule {
    private var firebaseAnalytics: FirebaseAnalytics? = null

    fun initialize(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun getTrackerBuilder(): AnalyticsTrackerBuilder {
        if (firebaseAnalytics == null) {
            throw IllegalStateException("FirebaseAnalytics is not initialized. Call initialize() first.")
        }
        return AnalyticsTrackerBuilder(firebaseAnalytics!!)
    }
}
