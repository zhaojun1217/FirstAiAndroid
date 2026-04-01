package com.zhaojun.feature.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zhaojun.feature.home.model.Diary
import com.zhaojun.feature.home.state.HomeUiState
import com.zhaojun.feature.home.ui.MainTab

@Composable
fun HomePageContent(
    modifier: Modifier,
    uiState: HomeUiState,
    onTabSelected: (Int) -> Unit,
    onItemClick: (Diary) -> Unit
) {
    val currentList = uiState.cardList
    // 你说两个 tab 一样的列表，所以这里先直接共用
    // 如果以后要分开，改成根据 selectedTabIndex 返回不同 list 即可

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        HomeTabBar(
            tabs = uiState.tabs,
            selectedTabIndex = uiState.selectedTabIndex,
            onTabSelected = onTabSelected
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = currentList,
                key = { it.id }
            ) { item ->
                HomeCardItemView(
                    item = item,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}