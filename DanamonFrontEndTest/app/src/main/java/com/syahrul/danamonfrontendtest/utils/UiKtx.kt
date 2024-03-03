package com.syahrul.danamonfrontendtest.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syahrul.danamonfrontendtest.MainApp

val Fragment.appComponent
    inline get() = (requireActivity().application as MainApp).appComponent

val Context.appComponent
inline get() = (applicationContext as MainApp).appComponent

inline fun <T> MutableLiveData<T>.asImmutable(): LiveData<T> = this