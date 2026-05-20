package com.zhaojun.feature.home.ui.home

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhaojun.common.core.util.UiEvent
import com.zhaojun.feature.home.vm.HomeViewModel

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.getDiaryList()
    }

    LaunchedEffect(Unit) {
        vm.uiEvent.collect { event ->
            if (event is UiEvent.ShowToast) {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    HomePageContent(
        modifier = modifier,
        uiState = uiState,
        onTabSelected = vm::onTabSelected,
        onItemClick = { }
    )
}
