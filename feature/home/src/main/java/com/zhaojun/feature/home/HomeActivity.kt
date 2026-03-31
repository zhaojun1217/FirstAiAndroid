package com.zhaojun.feature.home

import HomeMainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.therouter.router.Route
import com.zhaojun.router.RouterPath

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
@Route(path = RouterPath.HOME)
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeMainScreen()
        }
    }
}