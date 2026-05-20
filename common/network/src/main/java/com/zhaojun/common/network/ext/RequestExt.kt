package com.zhaojun.common.network.ext

import com.zhaojun.common.network.model.ApiEnvelope
import com.zhaojun.common.network.model.ApiResult
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> ApiEnvelope<T>
): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccess()) {
            ApiResult.Success(response.data)
        } else {
            ApiResult.Error(
                code = response.code,
                message = response.message.ifBlank { "请求失败" }
            )
        }
    } catch (e: HttpException) {
        ApiResult.Error(
            code = e.code(),
            message = httpErrorMessage(e)
        )
    } catch (e: IOException) {
        ApiResult.Error(message = "网络连接失败")
    } catch (e: Exception) {
        ApiResult.Error(message = e.message ?: "未知异常")
    }
}

private fun httpErrorMessage(e: HttpException): String = when (e.code()) {
    401 -> "登录已过期，请重新登录"
    403 -> "无访问权限"
    404 -> "请求的资源不存在"
    in 500..599 -> "服务器繁忙，请稍后再试"
    else -> e.message() ?: "HTTP ${e.code()} 请求失败"
}
