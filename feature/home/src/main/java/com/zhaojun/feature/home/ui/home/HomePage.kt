package com.zhaojun.feature.home.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.zhaojun.feature.home.vm.HomeViewModel
import com.zhaojun.router.service.Navigator

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            vm.getDiaryList()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        HomePageContent(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
            onTabSelected = vm::onHomeFeedTabSelected,
            onItemClick = { },
        )

        FloatingActionButton(
            onClick = { Navigator.toDiaryUpload(context) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "上传日记",
            )
        }
    }
}
