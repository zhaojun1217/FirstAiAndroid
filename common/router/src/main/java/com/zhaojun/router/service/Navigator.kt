package com.zhaojun.router.service

import android.content.Context
import android.content.Intent
import com.therouter.TheRouter
import com.zhaojun.router.RouterPath

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
object Navigator {
    // 跳登录
    fun toLogin(context: Context, clearTask: Boolean = false) {
        TheRouter.build(RouterPath.LOGIN)
            .addFlags(
                if (clearTask) {
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK  // 清空栈
                } else {
                    0
                }
            )
            .navigation(context)
    }

    // 跳主页
    fun toHome(context: Context, clearTask: Boolean = false) {
        TheRouter.build(RouterPath.HOME)
            .addFlags(
                if (clearTask) {
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK  // 清空栈
                } else {
                    0
                }
            )
            .navigation(context)
    }


    fun toMine() {
        TheRouter.build(RouterPath.MINE).navigation()
    }

    /** 跳转上传日记页，[draftId] 为空表示新建 */
    fun toDiaryUpload(context: Context, draftId: Long? = null) {
        val builder = TheRouter.build(RouterPath.DIARY_UPLOAD)
        if (draftId != null) {
            builder.withLong(RouterPath.EXTRA_DRAFT_ID, draftId)
        }
        builder.navigation(context)
    }
}