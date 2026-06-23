package com.zhaojun.feature.home.state

import com.zhaojun.feature.home.data.local.entity.DiaryDraftEntity

/**
 * 上传日记页 UI 状态。
 */
data class DiaryUploadUiState(
    val draftId: Long? = null,
    val title: String = "",
    val content: String = "",
    val imageUrl: String = "",
    val weather: String = "",
    val isSubmitting: Boolean = false,
    val showDraftList: Boolean = false,
    val draftList: List<DiaryDraftEntity> = emptyList(),
    val titleError: String? = null,
    val contentError: String? = null,
    val imageUrlError: String? = null,
    val weatherError: String? = null,
)
