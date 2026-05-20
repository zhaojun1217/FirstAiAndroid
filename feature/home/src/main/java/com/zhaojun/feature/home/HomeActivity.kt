package com.zhaojun.feature.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.therouter.router.Route
import com.zhaojun.common.ui.theme.FirstAiTheme
import com.zhaojun.feature.home.ui.HomeMainScreen
import com.zhaojun.router.RouterPath
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = RouterPath.HOME)
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAiTheme {
                HomeMainScreen()
            }
        }
    }
}
