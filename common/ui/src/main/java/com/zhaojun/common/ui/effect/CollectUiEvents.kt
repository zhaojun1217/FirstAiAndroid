package com.zhaojun.common.ui.effect

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.zhaojun.common.core.util.UiEvent
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun CollectUiEvents(
    uiEvent: SharedFlow<UiEvent>,
    onNavigateToLogin: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onFinishCurrentPage: () -> Unit = {},
) {
    val context = LocalContext.current
    LaunchedEffect(uiEvent) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                UiEvent.NavigateToLogin -> onNavigateToLogin()
                UiEvent.NavigateToHome -> onNavigateToHome()
                UiEvent.FinishCurrentPage -> onFinishCurrentPage()
            }
        }
    }
}
