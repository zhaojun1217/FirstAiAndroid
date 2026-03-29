package com.zhaojun.firstaiandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.therouter.TheRouter
import com.zhaojun.router.RouterPath

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TheRouter.build(RouterPath.HOME).navigation(this)
        finish()
    }
}