package com.zhaojun.common.network.interceptor

import com.zhaojun.common.core.ext.AuthEventBus
import com.zhaojun.common.core.store.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton
/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val authEventBus: AuthEventBus,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.getToken()

        val request = chain.request()
            .newBuilder()
            .apply {
                if (!token.isNullOrEmpty()) {
                    header("Authorization", "Bearer $token")
                }
            }
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {

            // 1. 发送全局过期事件
            authEventBus.postExpired()

            // 2. 关闭请求，避免后续无效请求
            response.close()
        }
        return response
    }
}