package com.zhaojun.firstaiandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.therouter.router.Route
import com.zhaojun.common.ui.theme.FirstAiTheme
import com.zhaojun.router.service.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkLogin { isLogin ->

            if (isLogin) {
                Navigator.toHome()
            } else {
                Navigator.toLogin()
            }

            finish()
        }
    }
}