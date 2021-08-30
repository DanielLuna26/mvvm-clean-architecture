package com.moon.mvvm_clean.data.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.moon.mvvm_clean.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(@ApplicationContext context: Context) : UserPreferences {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences> = applicationContext.createDataStore(
        name = context.getString(R.string.data_store)
    )

    companion object {
        val keyTheme = preferencesKey<Boolean>("key_theme")
        val keyToken = preferencesKey<String>("token")
    }

    override val isNightMode: Flow<Boolean>
        get() = dataStore.data.map {
            it[keyTheme] ?: false
        }
    override val token: Flow<String?>
        get() = dataStore.data.map {
            it[keyToken]
        }

    override suspend fun toggleNightMode() {
        dataStore.edit {
            it[keyTheme] = !(it[keyTheme] ?: false)
        }
    }

    override suspend fun storeToken(token: String) {
        dataStore.edit {
            it[keyToken] = token
        }
    }
}