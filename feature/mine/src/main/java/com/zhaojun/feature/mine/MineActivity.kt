package com.zhaojun.feature.mine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.therouter.router.Route
import com.zhaojun.common.ui.theme.FirstAiTheme
import com.zhaojun.router.RouterPath

@Route(path = RouterPath.MINE)
class MineActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstAiTheme {
                MinePage()
            }
        }
    }
}
