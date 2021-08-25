package com.moon.mvvm_clean.domain.interactors

import com.moon.mvvm_clean.data.repositories.AuthRepository
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.body.SignIn
import com.moon.mvvm_clean.domain.response.Session
import com.moon.mvvm_clean.utils.DispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DoLoginUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : DoLoginUseCase {
    override fun invoke(signIn: SignIn) = flow {

        emit(Resource.Loading)

        delay(2000)

        //emit(repository.signIn(signIn))

        emit(Resource.Success(Response("OK", Session(""))))
    }
}