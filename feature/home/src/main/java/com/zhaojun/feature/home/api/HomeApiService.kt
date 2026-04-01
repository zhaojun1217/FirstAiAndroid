package com.zhaojun.feature.home.api

import com.zhaojun.feature.home.ext.HomeBizResponse
import com.zhaojun.feature.home.model.DiaryDetResponse
import com.zhaojun.feature.home.model.DiaryListResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/31
 */
interface HomeApiService {
    @GET("api/v1/diarys")
    suspend fun getDiaryList(@Query("page") page: Int): HomeBizResponse<DiaryListResponse>

    @GET("api/v1/diary/")
    suspend fun getDiaryDetail(@Query("id") id: Int): HomeBizResponse<DiaryDetResponse>
}