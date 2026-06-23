package com.zhaojun.feature.home.di

import android.content.Context
import androidx.room.Room
import com.zhaojun.feature.home.data.local.HomeDatabase
import com.zhaojun.feature.home.data.local.dao.DiaryDraftDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Home 模块 Room 数据库依赖注入。
 */
@Module
@InstallIn(SingletonComponent::class)
object HomeDatabaseModule {

    private const val DATABASE_NAME = "home_database"

    @Provides
    @Singleton
    fun provideHomeDatabase(
        @ApplicationContext context: Context,
    ): HomeDatabase {
        return Room.databaseBuilder(
            context,
            HomeDatabase::class.java,
            DATABASE_NAME,
        ).build()
    }

    @Provides
    fun provideDiaryDraftDao(
        database: HomeDatabase,
    ): DiaryDraftDao {
        return database.diaryDraftDao()
    }
}
