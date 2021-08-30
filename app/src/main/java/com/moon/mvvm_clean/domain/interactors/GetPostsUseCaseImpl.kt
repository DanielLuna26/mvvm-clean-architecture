package com.moon.mvvm_clean.domain.interactors

import com.moon.mvvm_clean.data.repositories.PostRepository
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.models.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPostsUseCaseImpl @Inject constructor(
    private val repository: PostRepository
) : GetPostsUseCase {
    override fun invoke(page: Int): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading)

        emit(repository.getPosts(page))
    }
}