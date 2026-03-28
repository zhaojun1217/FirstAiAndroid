package com.zhaojun.firstaiandroid

import androidx.lifecycle.ViewModel

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/26
 */
class MainViewModel : ViewModel() {
    inline fun run(block: () -> Unit) {
        block()
    }

    override fun toString(): String {
        run {
            print("sdfsf")
        }
        return super.toString()

    }
}