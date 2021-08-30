package com.moon.mvvm_clean.presentation.auth.sign_up

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.mvvm_clean.data.datastore.UserPreferences
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.body.SignIn
import com.moon.mvvm_clean.domain.interactors.DoSignUpUseCase
import com.moon.mvvm_clean.domain.response.Session
import com.moon.mvvm_clean.utils.isEmailValid
import com.moon.mvvm_clean.utils.isValidFieldRequired
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val doSignUpUseCase: DoSignUpUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    private var _result = MutableLiveData<Resource<Response<Session>>?>()
    val result : LiveData<Resource<Response<Session>>?> get() = _result

    val formErrors = ObservableArrayList<FormErrors>()

    fun isFormValid(): Boolean {
        formErrors.clear()

        if (!isEmailValid(email.value)) formErrors.add(FormErrors.EMAIL)
        if (!isValidFieldRequired(password.value)) formErrors.add(FormErrors.PASSWORD)
        if (!isValidFieldRequired(confirmPassword.value)) formErrors.add(FormErrors.CONFIRM_PASSWORD)
        if (confirmPassword.value != password.value) formErrors.add(FormErrors.CONFIRM_PASSWORD)

        return formErrors.isEmpty()
    }

    fun onClickSignUp() = viewModelScope.launch {
        doSignUpUseCase.invoke(SignIn(email.value!!, password.value!!))
            .collect {
                if (it is Resource.Success)
                    userPreferences.storeToken(it.value.data?.token!!)
                _result.postValue(it)
            }
    }

    fun resetResult() { _result.postValue(null) }

    enum class FormErrors {
        EMAIL,
        PASSWORD,
        CONFIRM_PASSWORD
    }
}