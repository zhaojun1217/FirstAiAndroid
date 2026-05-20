package com.zhaojun.common.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 统一业务响应包装。后端字段为 code / msg / data。
 */
@JsonClass(generateAdapter = true)
data class ApiEnvelope<T>(
    val code: Int,
    @Json(name = "msg") val message: String = "",
    val data: T?
) {
    fun isSuccess(): Boolean = code == 200
}
