package com.moon.mvvm_clean.data.repositories

import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.models.Post

interface PostRepository {
    suspend fun getPosts(page: Int) : Resource<List<Post>>
}