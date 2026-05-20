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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zhaojun.feature.home.model.Diary
import com.zhaojun.feature.home.state.HomeUiState

@Composable
fun HomePageContent(
    modifier: Modifier,
    uiState: HomeUiState,
    onTabSelected: (Int) -> Unit,
    onItemClick: (Diary) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        HomeTabBar(
            tabs = uiState.homeFeedTabs,
            selectedTabIndex = uiState.homeFeedTabIndex,
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
                items = uiState.cardList,
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
