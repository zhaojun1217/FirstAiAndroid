package com.zhaojun.feature.login.ext

import android.net.http.HttpException
import com.zhaojun.common.network.ext.safeApiCall
import com.zhaojun.common.network.model.ApiEnvelope
import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.feature.login.model.LoginBizResponse
import java.io.IOException

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
suspend fun <T> safeLoginApiCall(
    apiCall: suspend () -> LoginBizResponse<T>
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
        ApiResult.Error(message = e.message ?: "未知异常")
    }
}