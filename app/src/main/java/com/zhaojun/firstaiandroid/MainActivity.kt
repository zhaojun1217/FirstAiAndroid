package com.zhaojun.firstaiandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.zhaojun.router.service.Navigator

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Navigator.toLogin()
        finish()
    }
}