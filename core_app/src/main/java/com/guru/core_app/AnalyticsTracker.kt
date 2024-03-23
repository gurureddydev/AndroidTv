package com.guru.core_app

interface AnalyticsTracker {
    fun logEvent(eventName: String, params: Map<String, String>)
}
