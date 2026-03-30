package com.zhaojun.feature.login.repository

import com.zhaojun.common.network.retrofit.ServiceCreator
import com.zhaojun.feature.login.api.LoginApiService
import com.zhaojun.feature.login.model.LoginRequest
import jakarta.inject.Inject

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
class LoginRepository @Inject constructor(
    private val loginApiService: LoginApiService
) {
    suspend fun login(account: String, password: String) =
        loginApiService.login(LoginRequest(account, password))
}