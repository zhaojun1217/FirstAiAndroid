package com.zhaojun.common.network.model

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiEnvelope<T>(
    val code: Int,
    val message: String,
    val data: T?
) {
    fun isHttpSuccess(): Boolean = code == 200
}