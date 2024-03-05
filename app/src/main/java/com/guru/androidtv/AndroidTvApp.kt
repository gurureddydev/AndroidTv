package com.guru.androidtv

import android.app.Application
import com.guru.androidtv.api.ApiService
import com.guru.androidtv.api.RetrofitHelper
import com.guru.androidtv.api.TmDbRepo

class AndroidTvApp:Application() {
    lateinit var tmDbRepo : TmDbRepo
    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init(){
        val service = RetrofitHelper.getInstance().create(ApiService::class.java)
        tmDbRepo = TmDbRepo(service)
    }
}