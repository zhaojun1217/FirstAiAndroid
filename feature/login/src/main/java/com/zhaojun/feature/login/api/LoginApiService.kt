package com.zhaojun.feature.login.api

import com.zhaojun.common.network.model.BaseResponse
import com.zhaojun.feature.login.model.LoginRequest
import com.zhaojun.feature.login.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */

interface LoginApiService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>
}