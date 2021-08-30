package com.moon.mvvm_clean.data.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    val isNightMode: Flow<Boolean>
    val token: Flow<String?>

    suspend fun toggleNightMode()
    suspend fun storeToken(token: String)
}