package com.zhaojun.feature.home.state

import com.zhaojun.feature.home.model.Diary

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
data class HomeUiState(
    val selectedTabIndex: Int = 0,
    val loading: Boolean = false,
    val tabs: List<String> = listOf("小日记", "我喜欢"),
    val cardList: List<Diary> = emptyList()
)