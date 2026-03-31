package com.zhaojun.feature.home.ui

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/31
 */
sealed class MainTab(
    val title: String
) {
    data object Home : MainTab("首页")
    data object Discover : MainTab("发现")
    data object Setting : MainTab("设置")
}