package com.zhaojun.firstaiandroid

import android.app.Activity
import android.content.Intent
import android.util.Log.v
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.therouter.TheRouter
import com.zhaojun.common.core.ext.AuthEventBus
import com.zhaojun.router.RouterPath
import com.zhaojun.router.service.Navigator

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/04/01
 */
@Composable
fun MainContentView(
    vm: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    // 👇 页面一进来，执行登录检查
    LaunchedEffect(Unit) {
        vm.checkLogin()
    }

    // 👇 观察 VM 的跳转状态
    val navState by vm.navigateState.collectAsStateWithLifecycle()

    // 👇 根据状态跳转页面
    when (navState) {
        NavigateState.HOME -> Navigator.toHome()
        NavigateState.LOGIN -> Navigator.toLogin()
        NavigateState.LOADING -> {
            // 可以显示加载动画
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
    // 接收过期信息
    LaunchedEffect(Unit) {
        vm.authEventBus.authExpired.collect {
            vm.clearLoginInfo()
            // TheRouter 跳登录，清空任务栈
            Navigator.toLogin()
            // 结束当前 activity 栈
            (context as? Activity)?.finishAffinity()
        }
    }
}


