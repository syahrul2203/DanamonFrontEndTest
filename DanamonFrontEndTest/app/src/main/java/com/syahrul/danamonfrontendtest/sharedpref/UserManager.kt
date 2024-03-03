package com.syahrul.danamonfrontendtest.sharedpref

import android.content.SharedPreferences
import com.google.gson.Gson
import com.syahrul.danamonfrontendtest.dagger.DataModule
import com.syahrul.danamonfrontendtest.dagger.SharedPreferencesModule
import com.syahrul.danamonfrontendtest.room.UserRecord
import com.syahrul.danamonfrontendtest.utils.GsonPreference
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class UserManager @Inject constructor(
    @Named(SharedPreferencesModule.DEP_LOGIN_USER) private val userPrefs: SharedPreferences,
    gson: Gson,
    @Named(DataModule.DEP_NETWORK_DISPATCHER) private val ioDispatcher: CoroutineDispatcher
) {
    companion object {
        const val PREF_USER = "PREF_USER"
    }

    private var user by GsonPreference(
        userPrefs,
        PREF_USER,
        null,
        gson,
        UserRecord::class.java
    )

    fun storeUser(user: UserRecord) {
        this.user = user
    }

    fun getUserData() = user

    fun clearUserData() {
        user = null
    }

}