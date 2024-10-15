package com.sjbs2003.vridblogapp

import android.app.Application
import com.sjbs2003.vridblogapp.data.AppContainer
import com.sjbs2003.vridblogapp.data.DefaultContainer

class BlogApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer()
    }
}