package com.zhaojun.feature.login.api

import com.zhaojun.common.network.model.BizResponse
import com.zhaojun.feature.login.model.LoginRequest
import com.zhaojun.feature.login.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): BizResponse<LoginResponse>
}
