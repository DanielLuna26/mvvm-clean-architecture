package com.moon.mvvm_clean.domain

data class Response <out T> (
    val code: String,
    val data: T?
)