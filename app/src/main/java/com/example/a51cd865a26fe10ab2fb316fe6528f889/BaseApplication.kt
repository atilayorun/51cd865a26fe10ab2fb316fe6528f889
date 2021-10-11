package com.example.a51cd865a26fe10ab2fb316fe6528f889

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}