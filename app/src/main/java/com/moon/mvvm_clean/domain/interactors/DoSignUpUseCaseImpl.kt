package com.moon.mvvm_clean.domain.interactors

import com.moon.mvvm_clean.data.repositories.AuthRepository
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.body.SignIn
import com.moon.mvvm_clean.domain.response.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DoSignUpUseCaseImpl @Inject constructor (
    private val repository: AuthRepository
) : DoSignUpUseCase {
    override fun invoke(signIn: SignIn) = flow {
        emit(Resource.Loading)

        emit(repository.signUp(signIn))
    }
}