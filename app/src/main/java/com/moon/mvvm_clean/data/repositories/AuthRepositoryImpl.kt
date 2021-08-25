package com.moon.mvvm_clean.data.repositories

import com.moon.mvvm_clean.data.remote.AuthService
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.body.SignIn
import com.moon.mvvm_clean.domain.response.Session
import com.moon.mvvm_clean.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val dispatcher: DispatcherProvider
) : AuthRepository, BaseRepository() {
    override suspend fun signIn(signIn: SignIn) = withContext(dispatcher.io) {
        return@withContext safeApiCall { service.postSignIn(signIn) }
    }
}