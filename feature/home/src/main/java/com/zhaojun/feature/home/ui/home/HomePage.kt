package com.zhaojun.feature.home.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zhaojun.feature.home.state.HomeUiState
import com.zhaojun.feature.home.vm.HomeViewModel
import kotlinx.coroutines.launch

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = hiltViewModel()
) {
    var count by remember { mutableStateOf(0) }
    val uiState by vm.uiState.collectAsStateWithLifecycle()
//    val uiState1 by vm.uiState.collectAsState()
    val context = LocalContext.current
    val cusScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        vm.getDiaryList()
    }

    HomePageContent(
        modifier = modifier,
        uiState = uiState,
        onTabSelected = vm::onTabSelected,
        onItemClick = { item ->
            cusScope.launch {
            }
        }
    )
}

