package com.zhaojun.feature.home.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.therouter.TheRouter
import com.zhaojun.feature.home.vm.HomeViewModel
import com.zhaojun.router.service.Navigator
import kotlin.jvm.java

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */

@Composable
fun HomePage(vm: HomeViewModel = viewModel()) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(uiState.title)

        Button(
            onClick = {
                Toast.makeText(context, "111", Toast.LENGTH_LONG).show()
//                Navigator.toMine()
            }
        ) {
            Text("跳转到 Mine")
        }
    }
}