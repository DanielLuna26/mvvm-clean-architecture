package com.moon.mvvm_clean.domain

/**
 * Created by Daniel Luna on 4/8/21
 */
sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: String?
    ) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    val isLoading get() = this is Loading
    val data get() = (this as Success).value
}
