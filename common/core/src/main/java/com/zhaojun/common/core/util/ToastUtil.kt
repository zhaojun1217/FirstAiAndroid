package com.zhaojun.common.core.util

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 *     desc   :这个工具可以在任何地方hilt注入使用,但是为了MVVM的结构严谨,不建议在VM中使用
 */
@Singleton
class ToastUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var toast: Toast? = null

    fun show(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun showLong(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast?.show()
    }
}