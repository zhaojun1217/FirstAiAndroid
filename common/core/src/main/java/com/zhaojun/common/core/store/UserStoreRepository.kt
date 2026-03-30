package com.zhaojun.common.core.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val tokenFlow: Flow<String> = dataStore.data.map { prefs ->
        prefs[UserPreferencesKeys.TOKEN] ?: ""
    }

    val uidFlow: Flow<String> = dataStore.data.map { prefs ->
        prefs[UserPreferencesKeys.UID] ?: ""
    }

    val isLoginFlow: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[UserPreferencesKeys.IS_LOGIN] ?: false
    }

    suspend fun saveLoginInfo(token: String, uid: String) {
        dataStore.edit { prefs ->
            prefs[UserPreferencesKeys.TOKEN] = token
            prefs[UserPreferencesKeys.UID] = uid
            prefs[UserPreferencesKeys.IS_LOGIN] = true
        }
    }

    suspend fun clearLoginInfo() {
        dataStore.edit { prefs ->
            prefs.remove(UserPreferencesKeys.TOKEN)
            prefs.remove(UserPreferencesKeys.UID)
            prefs[UserPreferencesKeys.IS_LOGIN] = false
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { prefs ->
            prefs[UserPreferencesKeys.TOKEN] = token
        }
    }

    suspend fun saveUid(uid: String) {
        dataStore.edit { prefs ->
            prefs[UserPreferencesKeys.UID] = uid
        }
    }
}