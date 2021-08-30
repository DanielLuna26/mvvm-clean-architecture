package com.moon.mvvm_clean.domain.interactors

import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.body.SignIn
import com.moon.mvvm_clean.domain.response.Session
import kotlinx.coroutines.flow.Flow

interface DoSignUpUseCase {
    operator fun invoke(signIn: SignIn): Flow<Resource<Response<Session>>>
}