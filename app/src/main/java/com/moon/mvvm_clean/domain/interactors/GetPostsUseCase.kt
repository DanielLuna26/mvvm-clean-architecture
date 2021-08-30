package com.moon.mvvm_clean.domain.interactors

import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface GetPostsUseCase {
    operator fun invoke(page: Int): Flow<Resource<List<Post>>>
}