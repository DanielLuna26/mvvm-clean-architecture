package com.moon.mvvm_clean.di.modules

import com.moon.mvvm_clean.data.datastore.UserPreferences
import com.moon.mvvm_clean.data.datastore.UserPreferencesImpl
import com.moon.mvvm_clean.domain.interactors.DoLoginUseCase
import com.moon.mvvm_clean.domain.interactors.DoLoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface PresentationModule {
    @Binds
    fun bindUserPreferences(userPreferences: UserPreferencesImpl) : UserPreferences

    @Binds
    fun bindDoLoginUseCase(doLoginUseCase: DoLoginUseCaseImpl) : DoLoginUseCase
}