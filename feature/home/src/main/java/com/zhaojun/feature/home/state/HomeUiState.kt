package com.zhaojun.feature.home.state

import com.zhaojun.feature.home.model.Diary

data class HomeUiState(
    /** 底部导航栏选中项：0=首页 1=发现 2=设置 */
    val mainTabIndex: Int = 0,
    /** 首页内顶部 Tab：小日记 / 我喜欢 */
    val homeFeedTabIndex: Int = 0,
    val loading: Boolean = false,
    val homeFeedTabs: List<String> = listOf("小日记", "我喜欢"),
    val cardList: List<Diary> = emptyList(),
)
