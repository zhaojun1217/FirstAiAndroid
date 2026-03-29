package com.zhaojun.feature.login.model

import com.squareup.moshi.JsonClass

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
@JsonClass(generateAdapter = true)
data class LoginRequest(
    val username: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class LoginData(
    val userId: String,
    val token: String,
    val nickname: String?
)