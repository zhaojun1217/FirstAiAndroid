package com.zhaojun.feature.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zhaojun.feature.home.data.local.converter.SyncStatusConverter
import com.zhaojun.feature.home.data.local.dao.DiaryDraftDao
import com.zhaojun.feature.home.data.local.entity.DiaryDraftEntity

/**
 * Home 模块本地数据库。
 */
@Database(
    entities = [DiaryDraftEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(SyncStatusConverter::class)
abstract class HomeDatabase : RoomDatabase() {
    abstract fun diaryDraftDao(): DiaryDraftDao
}
