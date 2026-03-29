package com.zhaojun.feature.login.vm

import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.feature.login.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
class LoginViewModel : BaseViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun updateAccount(account: String) {
        _uiState.value = _uiState.value.copy(account = account)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login() {
        _uiState.value = _uiState.value.copy(isLoading = true)
    }
}