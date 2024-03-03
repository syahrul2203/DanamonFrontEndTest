package com.syahrul.danamonfrontendtest.viewmodel.helper

import androidx.lifecycle.MutableLiveData

class EventLiveData<T>: MutableLiveData<Event<T>>() {
    fun setEventValue(value: T) {
        super.setValue(Event(value))
    }
}