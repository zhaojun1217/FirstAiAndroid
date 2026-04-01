package com.zhaojun.feature.home.ui.discover

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/04/01
 */
@Composable
fun RowListPage(
    title: String,
    id: Int
) {
    val mockList = List(20) { index -> "$title 第 ${index + 1} 个页面" }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(mockList.size) { item ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = item.toString())
            }
        }
    }
}