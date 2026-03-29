package com.zhaojun.common.network.retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zhaojun.common.network.config.NetworkConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
object RetrofitProvider {
// 如果类(request)没有开启JsonClass,可以用这个反射进行序列化
//    private val moshi: Moshi by lazy {
//        Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//    }

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