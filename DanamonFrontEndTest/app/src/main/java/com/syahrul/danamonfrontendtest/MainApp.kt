package com.syahrul.danamonfrontendtest

import android.app.Application
import com.syahrul.danamonfrontendtest.dagger.DaggerAppComponent

class MainApp: Application() {

    val appComponent = DaggerAppComponent.builder()
        .mainApp(this)
        .build()

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)

    }
}