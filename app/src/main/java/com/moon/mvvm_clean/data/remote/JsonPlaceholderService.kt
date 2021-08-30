package com.moon.mvvm_clean.data.remote

import com.moon.mvvm_clean.domain.models.Post
import retrofit2.http.GET
import retrofit2.http.Url

interface JsonPlaceholderService {

    @GET
    suspend fun getPosts(
        @Url url: String
    ) : List<Post>
}