package com.zhaojun.common.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BizResponse<T>(
    val code: Int,
    val msg: String,
    val data: T?
) {
    fun isBizSuccess(): Boolean = code == 200
}
