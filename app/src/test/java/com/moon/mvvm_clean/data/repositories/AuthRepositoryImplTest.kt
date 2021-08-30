package com.moon.mvvm_clean.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lumston.enone.MockServerBaseTest
import com.moon.mvvm_clean.CoroutineTestRule
import com.moon.mvvm_clean.data.remote.AuthService
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.domain.body.SignIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryImplTest: MockServerBaseTest() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    override fun isMockServerEnabled() = true

    private lateinit var repository: AuthRepository

    private lateinit var api: AuthService

    @Before
    fun setUpRep() {
        MockitoAnnotations.initMocks(this)

        api = provideTestApiService(AuthService::class.java)

        repository = AuthRepositoryImpl(api, testCoroutineRule.testDispatcherProvider)
    }

    @Test
    fun `Send request to sign in endpoint with correct user`() = runBlocking {
        mockHttpResponse("sign_in_200_response.json", HttpURLConnection.HTTP_OK)

        val response = repository.signIn(SignIn("d2@gmail.com", "asa211"))

        assertTrue(response is Resource.Success)
        assertEquals((response as Resource.Success).value.code, "OK")
    }

    @Test
    fun `Send request to sign in endpoint with incorrect user`() = runBlocking {
        mockHttpResponse("sign_in_401_response.json", HttpURLConnection.HTTP_UNAUTHORIZED)

        val response = repository.signIn(SignIn("d@gmail.com", "asa211"))

        assertTrue(response is Resource.Failure)
        assertEquals((response as Resource.Failure).errorCode, HttpURLConnection.HTTP_UNAUTHORIZED)
    }

    @Test
    fun `Send request to sign in endpoint with incorrect data`() = runBlocking {
        mockHttpResponse("sign_in_422_response.json", 422)

        val response = repository.signIn(SignIn("d2@gmail.com", ""))

        assertTrue(response is Resource.Failure)
        assertEquals((response as Resource.Failure).errorCode, 422)
    }

    @Test
    fun `Send request to sign in endpoint with correct data but response an error`() = runBlocking {
        mockHttpResponse("sign_in_500_response.json", HttpURLConnection.HTTP_INTERNAL_ERROR)

        val response = repository.signIn(SignIn("d@gmail.com", "asa11"))

        assertTrue(response is Resource.Failure)
        assertEquals((response as Resource.Failure).errorCode, HttpURLConnection.HTTP_INTERNAL_ERROR)
    }
}