package com.zhaojun.feature.home.repository

import android.R.attr.password
import android.util.Log
import android.util.Log.e
import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.feature.home.api.HomeApiService
import com.zhaojun.feature.home.ext.safeHomeApiCall
import com.zhaojun.feature.home.model.DiaryDetResponse
import com.zhaojun.feature.home.model.DiaryListResponse
import com.zhaojun.feature.home.state.HomeUiState
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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

    fun texstf(): Flow<HomeUiState> = flow {

    }
}