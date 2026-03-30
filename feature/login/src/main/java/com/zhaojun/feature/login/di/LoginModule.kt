package com.zhaojun.feature.login.di
import com.zhaojun.feature.login.api.LoginApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/30
 */
@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginApiService(
        retrofit: Retrofit
    ): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }
}