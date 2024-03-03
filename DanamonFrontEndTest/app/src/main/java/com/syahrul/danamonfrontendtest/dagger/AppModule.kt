package com.syahrul.danamonfrontendtest.dagger

import android.app.Application
import android.content.Context
import com.syahrul.danamonfrontendtest.MainApp
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule.Bindings::class])
class AppModule {

    @Module
    interface Bindings {
    }

    @Provides
    fun applicationContext(mainApp: MainApp): Context = mainApp

    @Provides
    fun application(mainApp: MainApp): Application = mainApp

}