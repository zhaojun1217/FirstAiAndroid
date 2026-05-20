package com.zhaojun.feature.login.repository

import com.zhaojun.common.network.ext.safeApiCall
import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.feature.login.api.LoginApiService
import com.zhaojun.feature.login.model.LoginRequest
import com.zhaojun.feature.login.model.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginApiService: LoginApiService
) {
    suspend fun login(
        account: String,
        password: String
    ): ApiResult<LoginResponse> {
        return safeApiCall {
            loginApiService.login(
                LoginRequest(
                    username = account,
                    password = password
                )
            )
        }
    }
}
