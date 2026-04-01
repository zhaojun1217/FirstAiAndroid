package com.zhaojun.feature.home.ext

import com.zhaojun.common.network.model.ApiResult
import kotlin.coroutines.cancellation.CancellationException

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
suspend fun <T> safeHomeApiCall(
    block: suspend () -> HomeBizResponse<T>
): ApiResult<T> {
    return try {
        val resp = block()
        when (resp.code) {
            200 -> ApiResult.Success(resp.data)
            401 -> {
                ApiResult.Error(code = 401, message = resp.msg)
            }

            else -> {
                ApiResult.Error(code = resp.code, message = resp.msg)
            }
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        ApiResult.Error(message = e.message ?: "网络异常")
    }
}