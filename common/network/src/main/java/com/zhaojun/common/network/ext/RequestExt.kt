package com.zhaojun.common.network.ext

import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.common.network.model.BaseResponse
import com.zhaojun.common.network.exception.ApiException
import retrofit2.HttpException
import java.io.IOException

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> BaseResponse<T>
): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccess()) {
            ApiResult.Success(response.data)
        } else {
            ApiResult.Error(
                code = response.code,
                message = response.msg.ifBlank { "请求失败" }
            )
        }
    } catch (e: ApiException) {
        // todo 这里以后根据业务code定义不同的错误
        ApiResult.Error(
            code = e.code,
            message = e.message
        )
    } catch (e: HttpException) {
        ApiResult.Error(
            code = e.code(),
            message = e.message ?: "HTTP ${e.code()} 请求失败"
        )
    } catch (e: IOException) {
        ApiResult.Error(
            message = "网络连接失败"
        )
    } catch (e: Exception) {
        ApiResult.Error(
            message = e.message ?: "未知异常"
        )
    }
}