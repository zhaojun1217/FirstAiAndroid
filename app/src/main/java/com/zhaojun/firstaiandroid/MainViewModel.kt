package com.zhaojun.firstaiandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhaojun.common.core.ext.AuthEventBus
import com.zhaojun.common.core.store.UserPreferencesRepository
import com.zhaojun.common.network.interceptor.DefaultTokenProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val defaultTokenProvider: DefaultTokenProvider,
    val authEventBus: AuthEventBus
) : ViewModel() {
    // 定义跳转状态
    private val _navigateState = MutableStateFlow<NavigateState>(NavigateState.LOADING)
    val navigateState = _navigateState.asStateFlow()

    fun checkLogin() {
        viewModelScope.launch {
            val token = userPreferencesRepository.tokenFlow.first()
            // 先同步到内存
            defaultTokenProvider.updateToken(token)

            val isLogin = token.isNotBlank() // 异步判断登录

            // 更新状态 → UI 自动跳转
            _navigateState.value = if (isLogin) NavigateState.HOME else NavigateState.LOGIN
        }
    }

    fun clearLoginInfo() {
        viewModelScope.launch {
            defaultTokenProvider.clearToken()
            userPreferencesRepository.clearLoginInfo()
        }
    }
}