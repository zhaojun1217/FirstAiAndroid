package com.zhaojun.common.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */

class HeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("platform", "android")
            .addHeader("appVersion", "1.0.0")
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}