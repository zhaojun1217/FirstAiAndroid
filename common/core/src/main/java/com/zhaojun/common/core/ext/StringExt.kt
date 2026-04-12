package com.zhaojun.common.core.ext

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/04/12
 */
fun String.log(tag: String = "APP") {
    android.util.Log.d(tag, this)
}