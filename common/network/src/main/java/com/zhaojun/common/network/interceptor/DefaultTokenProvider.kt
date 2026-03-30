package com.zhaojun.common.network.interceptor
import javax.inject.Inject
import javax.inject.Singleton
/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
@Singleton
class DefaultTokenProvider @Inject constructor() : TokenProvider {

    private var token: String? = null

    override fun getToken(): String? = token

    fun updateToken(newToken: String?) {
        token = newToken
    }
}