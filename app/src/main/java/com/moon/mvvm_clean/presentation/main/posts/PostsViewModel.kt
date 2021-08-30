package com.moon.mvvm_clean.presentation.main.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.interactors.GetPostsUseCase
import com.moon.mvvm_clean.domain.models.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
): ViewModel() {

    private val _result = MutableStateFlow<Resource<List<Post>>?>(null)
    val result: StateFlow<Resource<List<Post>>?> get() = _result
    private val _posts = MutableLiveData<MutableList<Post>>(mutableListOf())
    val posts : LiveData<MutableList<Post>> get() = _posts

    private var page: Int = 0

    init {
        getPosts()
    }

    fun getPosts() = viewModelScope.launch {
        page++
        getPostsUseCase.invoke(page)
            .collect {
                _result.value = it
            }
    }

    fun addItems(response: Resource<List<Post>>) {
        result.value.let { result ->
            result ?: return@let
            if (response.data.isEmpty()) return@let

            val old = result.data as MutableList
            val new = response.data

            old.addAll(new)

            _posts.postValue(old)
        }
    }
}