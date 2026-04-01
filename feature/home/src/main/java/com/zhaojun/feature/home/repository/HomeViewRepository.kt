package com.zhaojun.feature.home.repository

import android.util.Log
import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.feature.home.api.HomeApiService
import com.zhaojun.feature.home.ext.safeHomeApiCall
import com.zhaojun.feature.home.model.DiaryDetResponse
import com.zhaojun.feature.home.model.DiaryListResponse
import jakarta.inject.Inject

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/31
 */
class HomeViewRepository @Inject constructor(
    private val homeApiService: HomeApiService,
) {

    suspend fun getDiaryList(pageIndex: Int): ApiResult<DiaryListResponse> {
        return safeHomeApiCall {
            homeApiService.getDiaryList(page = pageIndex)
        }
    }

    suspend fun getDiaryDet(diaryId: Int): ApiResult<DiaryDetResponse> {
        return safeHomeApiCall {
            homeApiService.getDiaryDetail(id = diaryId)
        }
    }

}