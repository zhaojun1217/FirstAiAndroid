package com.zhaojun.feature.home.model

import com.squareup.moshi.JsonClass

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/31
 */

@JsonClass(generateAdapter = true)
data class DiaryListResponse(
    val lists: List<Diary>,
    val total: Int
)


