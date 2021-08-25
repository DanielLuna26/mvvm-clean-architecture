package com.moon.mvvm_clean.data.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    val isNightMode: Flow<Boolean>

    suspend fun toggleNightMode()
}