package com.moon.mvvm_clean.data.repositories

import com.moon.mvvm_clean.data.remote.JsonPlaceholderService
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.models.Post
import com.moon.mvvm_clean.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor (
    private val api: JsonPlaceholderService,
    private val dispatcher: DispatcherProvider
) : BaseRepository(), PostRepository {
    override suspend fun getPosts(page: Int) = withContext(dispatcher.io) {
        return@withContext safeApiCall { api.getPosts("https://jsonplaceholder.typicode.com/posts?_embed=comments&_page=$page&_limit=10") }
    }
}