package com.zhaojun.common.core.store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
object UserPreferencesKeys {
    val TOKEN = stringPreferencesKey("token")
    val UID = stringPreferencesKey("uid")
    val IS_LOGIN = booleanPreferencesKey("is_login")
}