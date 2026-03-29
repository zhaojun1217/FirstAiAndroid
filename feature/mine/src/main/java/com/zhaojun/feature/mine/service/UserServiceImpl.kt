package com.zhaojun.feature.mine.service

import com.therouter.inject.ServiceProvider
import com.zhaojun.router.service.UserService

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */

@ServiceProvider
class UserServiceImpl : UserService {
    override fun getUserName(): String = "张三"
}