package com.zhaojun.common.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> RefreshLoadMoreList(
    data: List<T>,
    isRefreshing: Boolean,
    isLoadingMore: Boolean,
    hasMore: Boolean,
    modifier: Modifier = Modifier,
    preloadOffset: Int = 3,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    itemContent: @Composable (item: T) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(
        listState,
        data.size,
        hasMore,
        isLoadingMore,
        isRefreshing
    ) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalCount = layoutInfo.totalItemsCount

            totalCount > 0 && lastVisibleIndex >= totalCount - 1 - preloadOffset
        }.collect { shouldLoadMore ->
            if (
                shouldLoadMore &&
                hasMore &&
                !isLoadingMore &&
                !isRefreshing
            ) {
                onLoadMore()
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(data) { item ->
                itemContent(item)
            }

            if (isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (!hasMore && data.isNotEmpty()) {
                item {
                    Text(
                        text = "没有更多了",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}