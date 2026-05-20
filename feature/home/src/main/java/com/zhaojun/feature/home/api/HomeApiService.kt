package com.zhaojun.feature.home.api

import com.zhaojun.common.network.model.BizResponse
import com.zhaojun.feature.home.model.DiaryDetResponse
import com.zhaojun.feature.home.model.DiaryListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {
    @GET("api/v1/diarys")
    suspend fun getDiaryList(@Query("page") page: Int): BizResponse<DiaryListResponse>

    @GET("api/v1/diary/")
    suspend fun getDiaryDetail(@Query("id") id: Int): BizResponse<DiaryDetResponse>
}
