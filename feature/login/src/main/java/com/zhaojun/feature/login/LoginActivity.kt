package com.zhaojun.feature.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.therouter.router.Route
import com.zhaojun.common.core.ext.log
import com.zhaojun.common.ui.theme.FirstAiTheme
import com.zhaojun.feature.login.ui.LoginPage
import com.zhaojun.router.RouterPath
import dagger.hilt.android.AndroidEntryPoint

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
@AndroidEntryPoint
@Route(path = RouterPath.LOGIN)
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAiTheme {
                LoginPage()
            }
        }
        "这里是登陆页面".log()
    }
}