package com.moon.mvvm_clean.presentation.auth.sign_in

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.mvvm_clean.data.datastore.UserPreferences
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.body.SignIn
import com.moon.mvvm_clean.domain.interactors.DoLoginUseCase
import com.moon.mvvm_clean.domain.response.Session
import com.moon.mvvm_clean.utils.isEmailValid
import com.moon.mvvm_clean.utils.isValidFieldRequired
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val doLoginUseCase: DoLoginUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    val email = MutableStateFlow<String?>(null)
    val password = MutableStateFlow<String?>(null)

    private var _result = MutableStateFlow<Resource<Response<Session>>?>(null)
    val result : StateFlow<Resource<Response<Session>>?> get() = _result

    val formErrors = ObservableArrayList<FormErrors>()

    fun isFormValid() : Boolean {
        formErrors.clear()

        if (!isEmailValid(email.value)) formErrors.add(FormErrors.EMAIL)
        if (!isValidFieldRequired(password.value)) formErrors.add(FormErrors.PASSWORD)

        return formErrors.isEmpty()
    }

    fun onClickSignIn() = viewModelScope.launch {
        doLoginUseCase
            .invoke(SignIn(email.value!!, password.value!!))
            .collect {
                if (it is Resource.Success)
                    userPreferences.storeToken(it.value.data?.token!!)
                _result.value = it
            }
    }

    fun resetResult() { _result.value = null }

    enum class FormErrors {
        EMAIL,
        PASSWORD
    }
}