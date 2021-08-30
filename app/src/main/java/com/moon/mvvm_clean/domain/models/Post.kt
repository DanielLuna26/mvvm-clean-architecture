package com.moon.mvvm_clean.domain.models

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val comments: List<Comment>
)
