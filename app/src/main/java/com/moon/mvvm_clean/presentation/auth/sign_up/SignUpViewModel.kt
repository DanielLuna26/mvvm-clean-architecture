package com.moon.mvvm_clean.presentation.auth.sign_up

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.response.Session
import com.moon.mvvm_clean.presentation.auth.sign_in.SignInViewModel

class SignUpViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    private var _result = MutableLiveData<Resource<Response<Session>>?>()
    val result : LiveData<Resource<Response<Session>>?> get() = _result

    val formErrors = ObservableArrayList<SignInViewModel.FormErrorsLogin>()

    fun resetResult() { _result.postValue(null) }

    enum class FormErrorsSignUp {
        EMAIL,
        PASSWORD,
        CONFIRM_PASSWORD
    }
}