package com.zhaojun.common.network.model

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
sealed class ApiResult<out T> {
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Error(
        val code: Int? = null,
        val message: String
    ) : ApiResult<Nothing>()
}