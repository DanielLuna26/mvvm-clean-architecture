package com.moon.mvvm_clean.di

import android.content.Context
import com.moon.mvvm_clean.BuildConfig
import com.moon.mvvm_clean.data.remote.AuthService
import com.moon.mvvm_clean.data.remote.JsonPlaceholderService
import com.moon.mvvm_clean.data.remote.interceptors.NetworkConnectionInterceptor
import com.moon.mvvm_clean.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Provides
    fun provideBaseUrl() : String = BuildConfig.HOST

    @Provides
    fun provideNetworkInterceptor(@ApplicationContext context: Context) : NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor) : OkHttpClient =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(networkConnectionInterceptor)
                .build()
        } else {
            OkHttpClient
                .Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
        }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String) : Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit) : AuthService =
        retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideJsonPlaceholderService(retrofit: Retrofit) : JsonPlaceholderService =
        retrofit.create(JsonPlaceholderService::class.java)

    @Singleton
    @Provides
    fun provideDispatchers() : DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }

}