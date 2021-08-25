package com.moon.mvvm_clean.data.repositories

import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.body.SignIn
import com.moon.mvvm_clean.domain.response.Session

interface AuthRepository {
    suspend fun signIn(signIn: SignIn) : Resource<Response<Session>>
}