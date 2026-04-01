package com.zhaojun.feature.home.model

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/31
 */
data class DiaryDetResponse(
    val id: Int,
    val created_on: Long,
    val modified_on: Long,
    val deleted_on: Long,
    val title: String,
    val desc: String,
    val address: String,
    val video_url: String,
    val img_urls: List<String>,
    val content: String,
    val created_by: String,
    val modified_by: String,
    val state: Int,
    val tags: List<Tag>
)
