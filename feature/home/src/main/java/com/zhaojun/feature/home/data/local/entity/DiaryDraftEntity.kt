package com.zhaojun.feature.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhaojun.feature.home.data.local.SyncStatus

/**
 * 未提交成功的日记草稿实体。
 */
@Entity(tableName = "diary_draft")
data class DiaryDraftEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val imageUrl: String,
    val weather: String,
    val syncStatus: SyncStatus,
    val createdAt: Long,
    val updatedAt: Long,
    val lastError: String? = null,
)
