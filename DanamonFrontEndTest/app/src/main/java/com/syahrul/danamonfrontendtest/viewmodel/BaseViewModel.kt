package com.syahrul.danamonfrontendtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syahrul.danamonfrontendtest.viewmodel.helper.Event
import com.syahrul.danamonfrontendtest.viewmodel.helper.EventLiveData
import com.syahrul.danamonfrontendtest.utils.asImmutable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {

    protected val isInProgress = MutableLiveData<Boolean>()
    protected val error = EventLiveData<Throwable>()
    fun isInProgress() = isInProgress.asImmutable()
    fun getError() = error.asImmutable()

    protected fun launch(notifyInProgress: Boolean = true, notifyError: Boolean = true, task: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                if (notifyInProgress) isInProgress.value = true
                task()
            } catch (throwable: Throwable) {
                if (notifyError) error.value = Event(throwable)
            } finally {
                if (notifyInProgress) isInProgress.value = false
            }
        }
    }

}