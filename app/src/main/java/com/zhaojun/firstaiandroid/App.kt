package com.zhaojun.firstaiandroid

import android.app.Application
import com.zhaojun.common.core.store.UserPreferencesDataStore
import dagger.hilt.android.HiltAndroidApp

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var context: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}