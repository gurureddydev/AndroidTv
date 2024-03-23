package com.guru.core_app

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsTracker(private val firebaseAnalytics: FirebaseAnalytics) : AnalyticsTracker {
    override fun logEvent(eventName: String, params: Map<String, String>) {
        val bundle = Bundle().apply {
            params.forEach { (key, value) ->
                putString(key, value)
            }
        }
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}
