package com.zhaojun.feature.home.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhaojun.feature.home.data.local.entity.DiaryDraftEntity
import kotlinx.coroutines.flow.Flow

/**
 * 日记草稿本地数据访问对象。
 */
@Dao
interface DiaryDraftDao {

    @Query("SELECT * FROM diary_draft ORDER BY updatedAt DESC")
    fun observeAllDrafts(): Flow<List<DiaryDraftEntity>>

    @Query("SELECT * FROM diary_draft WHERE id = :draftId")
    suspend fun getById(draftId: Long): DiaryDraftEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(draft: DiaryDraftEntity): Long

    @Delete
    suspend fun delete(draft: DiaryDraftEntity)
}
