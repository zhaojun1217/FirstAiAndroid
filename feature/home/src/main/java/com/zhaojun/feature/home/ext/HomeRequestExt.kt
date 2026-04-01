package com.zhaojun.feature.home.ext

import android.util.Log
import com.zhaojun.common.network.model.ApiResult
import java.io.IOException

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
suspend fun <T> safeHomeApiCall(
    apiCall: suspend () -> HomeBizResponse<T>
): ApiResult<T> {
    return try {

        val response = apiCall()

        if (response.isBizSuccess()) {
            ApiResult.Success(response.data)
        } else {
            ApiResult.Error(
                code = response.code,
                message = response.msg
            )
        }

    } catch (e: IOException) {
        ApiResult.Error(message = "网络连接失败")

    } catch (e: Exception) {
        var message = e.message ?: "未知异常"
        Log.e("safeHomeApiCall", message)
        ApiResult.Error(message = message)
    }
}