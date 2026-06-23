package com.zhaojun.feature.home.model

import com.squareup.moshi.JsonClass

/**
 * 创建日记请求体（占位字段，后续与后端文档对齐）。
 */
@JsonClass(generateAdapter = true)
data class DiaryCreateRequest(
    val title: String,
    val content: String,
    val img_urls: List<String>,
    val weather: String,
)
