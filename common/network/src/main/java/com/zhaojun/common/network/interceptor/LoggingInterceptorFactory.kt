package com.zhaojun.common.network.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import com.zhaojun.common.network.BuildConfig
/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */

object LoggingInterceptorFactory {

    fun create(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}