package com.moon.mvvm_clean.data.remote

import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.body.SignIn
import com.moon.mvvm_clean.domain.response.Session
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("api/sign-in")
    suspend fun postSignIn(
        @Body signIn: SignIn
    ) : Response<Session>
}