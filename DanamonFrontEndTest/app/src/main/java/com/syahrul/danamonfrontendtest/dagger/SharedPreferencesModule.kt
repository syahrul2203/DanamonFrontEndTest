package com.syahrul.danamonfrontendtest.dagger

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SharedPreferencesModule {

    companion object {
        const val DEP_LOGIN_USER = "DEP_LOGIN_USER"
    }

    @Provides
    @Named(DEP_LOGIN_USER)
    fun userManagerPrefs(context: Context): SharedPreferences = context.getSharedPreferences(DEP_LOGIN_USER, 0)

}