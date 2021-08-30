package com.moon.mvvm_clean.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.mvvm_clean.data.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {
    private var _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean> get() = _isLogged

    init {
        viewModelScope.launch {
            userPreferences.token.collect {
                _isLogged.postValue(!it.isNullOrEmpty())
            }
        }
    }
}