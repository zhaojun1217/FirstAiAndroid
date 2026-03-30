package com.zhaojun.firstaiandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhaojun.common.core.store.UserPreferencesRepository
import com.zhaojun.common.network.interceptor.DefaultTokenProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/26
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val defaultTokenProvider: DefaultTokenProvider
) : ViewModel() {

    fun checkLogin(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val token = userPreferencesRepository.tokenFlow.first()
            // 先同步到内存
            defaultTokenProvider.updateToken(token)
            // 再判断登录状态
            onResult(token.isNotBlank())
        }
    }
}