package com.zhaojun.feature.login.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val token: String? = null,
    @Json(name = "uid") val uid: String? = null,
    @Json(name = "user_id") val userId: String? = null,
) {
    /** 优先使用接口返回的 uid，否则回退为登录账号 */
    fun resolvedUid(fallbackAccount: String): String =
        uid?.takeIf { it.isNotBlank() }
            ?: userId?.takeIf { it.isNotBlank() }
            ?: fallbackAccount
}
