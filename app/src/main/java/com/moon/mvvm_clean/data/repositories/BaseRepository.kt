package com.moon.mvvm_clean.data.repositories

import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.utils.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException
import java.net.UnknownServiceException

/**
 * Created by Daniel Luna on 4/9/21
 */
abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : Resource<T> {
        return try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when(throwable) {
                    is HttpException ->
                        Resource
                            .Failure(
                                false,
                                throwable.response()?.code(),
                                throwable.response()?.message()
                            )
                    is NoInternetException ->
                        Resource.Failure(true, null, null)
                    else ->
                        Resource.Failure(false, null, throwable.message)
                }
            }
    }
}