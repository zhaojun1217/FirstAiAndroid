package com.zhaojun.feature.login.vm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.feature.login.repository.LoginRepository
import com.zhaojun.feature.login.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
class LoginViewModel : BaseViewModel() {
    private val repository = LoginRepository()
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun updateAccount(account: String) {
        _uiState.value = _uiState.value.copy(account = account)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login() {
        val state = _uiState.value
        if (state.account.isBlank() || state.password.isBlank()) return

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, errorMsg = null)
            runCatching {
                repository.login(state.account, state.password)
            }.onSuccess { resp ->
                Log.d("Login Success", resp.toString())
                _uiState.value = _uiState.value.copy(isLoading = false, loginSuccess = true)
                // 这里后面再处理 token / 用户信息

            }.onFailure { e ->
                Log.d("Login Error", e.toString())
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMsg = e.message
                )
            }
        }
    }
}