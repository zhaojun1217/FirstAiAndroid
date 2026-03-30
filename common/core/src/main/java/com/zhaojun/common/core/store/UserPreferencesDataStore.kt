package com.zhaojun.common.core.store

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
private const val DATASTORE_NAME = "user_prefs"
private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

class UserPreferencesDataStore(
    private val context: Context
) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_UID = stringPreferencesKey("uid")
        private val KEY_IS_LOGIN = booleanPreferencesKey("is_login")
        private val KEY_USER_NAME = stringPreferencesKey("user_name")
        private val KEY_EXPIRE_TIME = longPreferencesKey("expire_time")
    }

    val tokenFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_TOKEN] ?: ""
    }

    val uidFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_UID] ?: ""
    }

    val isLoginFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_IS_LOGIN] ?: false
    }

    val userNameFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_USER_NAME] ?: ""
    }

    val expireTimeFlow: Flow<Long> = context.dataStore.data.map { prefs ->
        prefs[KEY_EXPIRE_TIME] ?: 0L
    }

    suspend fun saveLoginInfo(
        token: String,
        uid: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_UID] = uid
            prefs[KEY_IS_LOGIN] = true
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
        }
    }

    suspend fun saveUid(uid: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_UID] = uid
        }
    }

    suspend fun saveUserName(userName: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_NAME] = userName
        }
    }

    suspend fun saveExpireTime(time: Long) {
        context.dataStore.edit { prefs ->
            prefs[KEY_EXPIRE_TIME] = time
        }
    }

    suspend fun clearLoginInfo() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_TOKEN)
            prefs.remove(KEY_UID)
            prefs[KEY_IS_LOGIN] = false
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}