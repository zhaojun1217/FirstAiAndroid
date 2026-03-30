package com.zhaojun.common.network.exception

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
class ApiException(
    val code: Int,
    override val message: String
) : RuntimeException(message)