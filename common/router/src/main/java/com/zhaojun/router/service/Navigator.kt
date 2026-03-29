package com.zhaojun.router.service

import com.therouter.TheRouter
import com.zhaojun.router.RouterPath

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
object Navigator {
    fun toLogin() {
        TheRouter.build(RouterPath.LOGIN).navigation()
    }

    fun toHome() {
        TheRouter.build(RouterPath.HOME).navigation()
    }

    fun toMine() {
        TheRouter.build(RouterPath.MINE).navigation()
    }
}