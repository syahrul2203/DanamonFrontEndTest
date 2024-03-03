package com.syahrul.danamonfrontendtest.utils

import android.text.TextUtils
import android.util.Base64
import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.Date


object Utility {

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isPasswordValid(target: String): Boolean {
        return target.length >= 8
    }

    fun String.decodePassword(): String {
        return Base64.decode(this, Base64.DEFAULT).toString(charset("UTF-8"))
    }

    fun String.encodePassword(): String {
        return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT)
    }

    fun generateUserId() : Long {
        val sdf = SimpleDateFormat("ddMMyyyyHHmmss")
        return sdf.format(Date()).toLong()
    }

}