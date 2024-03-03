package com.syahrul.danamonfrontendtest.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class GsonPreference<T>(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: T?,
    private val gson: Gson,
    private val clazz: Class<T>
) : ReadWriteProperty<Any, T?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return preferences.getString(name, null) ?. let {
            gson.fromJson(it, clazz)
        } ?: defaultValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        val json = value ?. let { gson.toJson(it) }
        preferences.edit().putString(name, json).apply()
    }
}