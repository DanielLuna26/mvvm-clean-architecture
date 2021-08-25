package com.moon.mvvm_clean.presentation.auth.sign_in

import android.util.Log
import androidx.annotation.StringRes
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.mvvm_clean.R
import com.moon.mvvm_clean.data.datastore.UserPreferences
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.body.SignIn
import com.moon.mvvm_clean.domain.interactors.DoLoginUseCase
import com.moon.mvvm_clean.domain.response.Session
import com.moon.mvvm_clean.utils.isEmailValid
import com.moon.mvvm_clean.utils.isValidFieldRequired
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val doLoginUseCase: DoLoginUseCase) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private var _result = MutableLiveData<Resource<Response<Session>>?>()
    val result : LiveData<Resource<Response<Session>>?> get() = _result

    val formErrors = ObservableArrayList<FormErrorsLogin>()

    fun isFormValid() : Boolean {
        formErrors.clear()

        if (!isEmailValid(email.value)) formErrors.add(FormErrorsLogin.EMAIL)
        if (!isValidFieldRequired(password.value)) formErrors.add(FormErrorsLogin.PASSWORD)

        return formErrors.isEmpty()
    }

    fun onClickSignIn() = viewModelScope.launch {
        doLoginUseCase
            .invoke(SignIn(email.value!!, password.value!!))
            .collect {
                _result.postValue(it)
            }
    }

    fun resetResult() { _result.postValue(null) }

    enum class FormErrorsLogin {
        EMAIL,
        PASSWORD
    }
}