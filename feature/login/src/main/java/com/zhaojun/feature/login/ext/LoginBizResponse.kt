package com.zhaojun.feature.login.ext

import com.squareup.moshi.JsonClass

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
@JsonClass(generateAdapter = true)
data class LoginBizResponse<T>(
    val code: Int,
    val msg: String,
    val data: T?
) {
    fun isBizSuccess(): Boolean = code == 200
}