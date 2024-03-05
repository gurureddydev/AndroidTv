package com.guru.androidtv

import android.app.Application
import com.guru.androidtv.api.RetrofitInstance
import com.guru.androidtv.api.TmDbRepo

class AndroidTvApp : Application() {
    lateinit var tmDbRepo: TmDbRepo
    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init() {
        val service = RetrofitInstance.getApiService()
        tmDbRepo = TmDbRepo(service)
    }
}