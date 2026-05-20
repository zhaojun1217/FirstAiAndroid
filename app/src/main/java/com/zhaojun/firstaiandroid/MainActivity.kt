package com.zhaojun.firstaiandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zhaojun.common.core.ext.log
import com.zhaojun.common.ui.theme.FirstAiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAiTheme {
                MainContentView()
            }
        }
        "项目启动了".log()
    }
}
