package com.zhaojun.common.network.retrofit

import com.squareup.moshi.Moshi
import com.zhaojun.common.network.config.NetworkConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
object RetrofitProvider {

    private val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(OkHttpProvider.client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}