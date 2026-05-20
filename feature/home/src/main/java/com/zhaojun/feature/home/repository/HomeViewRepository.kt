package com.zhaojun.feature.home.repository

import com.zhaojun.common.network.ext.safeApiCall
import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.feature.home.api.HomeApiService
import com.zhaojun.feature.home.model.DiaryDetResponse
import com.zhaojun.feature.home.model.DiaryListResponse
import javax.inject.Inject

class HomeViewRepository @Inject constructor(
    private val homeApiService: HomeApiService,
) {

    suspend fun getDiaryList(pageIndex: Int): ApiResult<DiaryListResponse> {
        return safeApiCall {
            homeApiService.getDiaryList(page = pageIndex)
        }
    }

    suspend fun getDiaryDet(diaryId: Int): ApiResult<DiaryDetResponse> {
        return safeApiCall {
            homeApiService.getDiaryDetail(id = diaryId)
        }
    }
}
