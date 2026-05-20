package com.zhaojun.common.network.session

import com.zhaojun.common.core.store.UserPreferencesRepository
import com.zhaojun.common.network.interceptor.DefaultTokenProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val tokenProvider: DefaultTokenProvider,
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    suspend fun clearSession() {
        tokenProvider.clearToken()
        userPreferencesRepository.clearLoginInfo()
    }
}
