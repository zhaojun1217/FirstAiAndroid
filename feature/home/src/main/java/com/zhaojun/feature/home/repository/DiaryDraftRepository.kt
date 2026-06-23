package com.zhaojun.feature.home.repository

import com.zhaojun.feature.home.data.local.dao.DiaryDraftDao
import com.zhaojun.feature.home.data.local.entity.DiaryDraftEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 日记草稿本地仓库，封装 Room 读写。
 */
class DiaryDraftRepository @Inject constructor(
    private val diaryDraftDao: DiaryDraftDao,
) {

    fun observeAllDrafts(): Flow<List<DiaryDraftEntity>> = diaryDraftDao.observeAllDrafts()

    suspend fun getDraftById(draftId: Long): DiaryDraftEntity? = diaryDraftDao.getById(draftId)

    suspend fun saveDraft(draft: DiaryDraftEntity): Long = diaryDraftDao.upsert(draft)

    suspend fun deleteDraft(draft: DiaryDraftEntity) = diaryDraftDao.delete(draft)
}
