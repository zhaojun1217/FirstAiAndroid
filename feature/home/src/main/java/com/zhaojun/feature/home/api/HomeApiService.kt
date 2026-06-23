package com.zhaojun.feature.home.api

import com.zhaojun.common.network.model.ApiEnvelope
import com.zhaojun.feature.home.model.DiaryCreateRequest
import com.zhaojun.feature.home.model.DiaryDetResponse
import com.zhaojun.feature.home.model.DiaryListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeApiService {
    @GET("api/v1/diarys")
    suspend fun getDiaryList(@Query("page") page: Int): ApiEnvelope<DiaryListResponse>

    @GET("api/v1/diary/")
    suspend fun getDiaryDetail(@Query("id") id: Int): ApiEnvelope<DiaryDetResponse>

    /** 创建日记（占位路径，后续与后端文档对齐） */
    @POST("api/v1/diary/create")
    suspend fun createDiary(@Body request: DiaryCreateRequest): ApiEnvelope<DiaryDetResponse>
}
