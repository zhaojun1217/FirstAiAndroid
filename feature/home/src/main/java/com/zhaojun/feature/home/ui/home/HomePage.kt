package com.zhaojun.feature.home.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhaojun.feature.home.vm.HomeViewModel

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.getDiaryList()
    }

    HomePageContent(
        modifier = modifier,
        uiState = uiState,
        onTabSelected = vm::onHomeFeedTabSelected,
        onItemClick = { }
    )
}
