package com.zhaojun.common.network.retrofit

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */

object ServiceCreator {

    inline fun <reified T> create(): T {
        return RetrofitProvider.retrofit.create(T::class.java)
    }
}