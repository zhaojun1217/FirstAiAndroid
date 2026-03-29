package com.zhaojun.feature.login.repository

import com.zhaojun.common.network.retrofit.ServiceCreator
import com.zhaojun.feature.login.api.LoginApiService
import com.zhaojun.feature.login.model.LoginRequest

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
class LoginRepository(
    private val api: LoginApiService = ServiceCreator.create<LoginApiService>()
) {

    suspend fun login(account: String, password: String) =
        api.login(LoginRequest(username = account, password = password))
}