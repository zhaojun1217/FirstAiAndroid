package com.zhaojun.common.network.interceptor

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
interface TokenProvider {
    fun getToken(): String?
}