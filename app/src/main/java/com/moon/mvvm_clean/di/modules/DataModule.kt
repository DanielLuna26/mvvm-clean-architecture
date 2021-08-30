package com.moon.mvvm_clean.di.modules

import com.moon.mvvm_clean.data.datastore.UserPreferences
import com.moon.mvvm_clean.data.datastore.UserPreferencesImpl
import com.moon.mvvm_clean.data.repositories.AuthRepository
import com.moon.mvvm_clean.data.repositories.AuthRepositoryImpl
import com.moon.mvvm_clean.data.repositories.PostRepository
import com.moon.mvvm_clean.data.repositories.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl) : AuthRepository

    @Binds
    fun bindPostRepository(postRepository: PostRepositoryImpl) : PostRepository
}