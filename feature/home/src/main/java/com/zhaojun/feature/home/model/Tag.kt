package com.zhaojun.feature.home.model

import com.squareup.moshi.JsonClass

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/31
 */
@JsonClass(generateAdapter = true)
data class Tag(
    val id: Int,
    val created_on: Long,
    val modified_on: Long,
    val deleted_on: Long,
    val name: String,
    val created_by: String,
    val modified_by: String,
    val state: Int
)
