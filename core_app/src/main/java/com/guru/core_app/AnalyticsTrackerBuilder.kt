package com.guru.core_app

import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsTrackerBuilder(private val firebaseAnalytics: FirebaseAnalytics) {
    fun build(): AnalyticsTracker {
        return FirebaseAnalyticsTracker(firebaseAnalytics)
    }
}
