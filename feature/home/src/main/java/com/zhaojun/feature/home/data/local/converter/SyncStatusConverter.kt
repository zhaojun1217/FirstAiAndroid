package com.zhaojun.feature.home.data.local.converter

import androidx.room.TypeConverter
import com.zhaojun.feature.home.data.local.SyncStatus

/**
 * Room 枚举类型转换器。
 */
class SyncStatusConverter {

    @TypeConverter
    fun fromSyncStatus(status: SyncStatus): String = status.name

    @TypeConverter
    fun toSyncStatus(value: String): SyncStatus = SyncStatus.valueOf(value)
}
