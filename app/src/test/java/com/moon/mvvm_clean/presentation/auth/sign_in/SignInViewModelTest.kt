package com.moon.mvvm_clean.presentation.auth.sign_in

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moon.mvvm_clean.CoroutineTestRule
import com.moon.mvvm_clean.data.datastore.UserPreferences
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.Response
import com.moon.mvvm_clean.domain.body.SignIn
import com.moon.mvvm_clean.domain.interactors.DoLoginUseCase
import com.moon.mvvm_clean.domain.response.Session
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SignInViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    @Mock
    private lateinit var doLoginUseCase: DoLoginUseCase

    @Mock
    private lateinit var userPreferences: UserPreferences

    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = SignInViewModel(doLoginUseCase, userPreferences)
    }

    @Test
    fun `Try to do login with email invalid`() {
        val email = "d2gmail.com"
        val password = "asa211"

        viewModel.email.value = email
        viewModel.password.value = password

        assertFalse(viewModel.isFormValid())
    }

    @Test
    fun `Try to do login with password invalid`() {
        val email = "d2gmail.com"
        val password = ""

        viewModel.email.value = email
        viewModel.password.value = password

        assertFalse(viewModel.isFormValid())
    }

    @Test
    fun `Do login with good response`() = testCoroutineRule.testDispatcher.runBlockingTest {
        val email = "d2@gmail.com"
        val password = "asa211"

        val response = Resource.Success(Response("OK", Session("token")))

        val flow = flow {
            emit(Resource.Loading)

            delay(1000)

            emit(response)
        }

        whenever(doLoginUseCase.invoke(SignIn(email, password))).thenReturn(flow)

        viewModel.email.value = email
        viewModel.password.value = password

        assertTrue(viewModel.isFormValid())

        viewModel.onClickSignIn()

        launch {
            viewModel.result.collectIndexed { index, value ->
                if (index == 0) assert(value is Resource.Loading)
                if (index == 1) assert(value is Resource.Success)
                cancel()
            }
        }
    }

    @Test
    fun `Do login with bad response`() = testCoroutineRule.testDispatcher.runBlockingTest {
        val email = "d2@gmail.com"
        val password = "asa211"

        val response = Resource.Failure(false, 500, "E_INTERNAL_SERVER_ERROR")

        val flow = flow {
            emit(Resource.Loading)

            delay(1000)

            emit(response)
        }

        whenever(doLoginUseCase.invoke(SignIn(email, password))).thenReturn(flow)

        viewModel.email.value = email
        viewModel.password.value = password

        assertTrue(viewModel.isFormValid())

        viewModel.onClickSignIn()

        launch {
            viewModel.result.collectIndexed { index, value ->
                if (index == 0) assert(value is Resource.Loading)
                if (index == 1) assert(value is Resource.Failure)
                cancel()
            }
        }
    }
}