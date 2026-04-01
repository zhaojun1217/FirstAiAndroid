package com.zhaojun.feature.home.ui.discover

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zhaojun.common.ui.component.CommonEmptyView
import kotlinx.coroutines.launch

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/31
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiscoverPage(
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        NewsTab("推荐", 1),
        NewsTab("热点", 2),
        NewsTab("科技", 3),
        NewsTab("财经", 4),
        NewsTab("体育", 5),
        NewsTab("本地", 6)
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabs.size }
    )
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SecondaryScrollableTabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = tab.title)
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            RowListPage(
                title = tabs[page].title, id = tabs[page].tabId
            )
        }
    }

//    CommonEmptyView()
}