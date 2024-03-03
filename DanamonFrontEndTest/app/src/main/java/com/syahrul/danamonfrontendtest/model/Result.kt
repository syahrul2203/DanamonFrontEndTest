package com.syahrul.danamonfrontendtest.model

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val exception: Throwable, val data: T? = null) : Result<T>()
    data class Loading<out T>(val data: T? = null) : Result<T>()

    fun getAvailableData(): R? {
        return (this as? Success)?.data
            ?: (this as? Error)?.data
            ?: (this as? Loading)?.data
    }
}