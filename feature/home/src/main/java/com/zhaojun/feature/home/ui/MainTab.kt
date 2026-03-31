package com.zhaojun.feature.home.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/31
 */
sealed class MainTab(
    val title: String,
    val icon: ImageVector
) {
    data object Home : MainTab("首页", Icons.Filled.Home)
    data object Discover : MainTab("发现", Icons.Filled.Search)
    data object Setting : MainTab("设置", Icons.Filled.Settings)
}