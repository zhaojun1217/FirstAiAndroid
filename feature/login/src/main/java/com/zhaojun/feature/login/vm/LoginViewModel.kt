package com.zhaojun.feature.login.vm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.common.core.store.UserPreferencesRepository
import com.zhaojun.feature.login.repository.LoginRepository
import com.zhaojun.feature.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : BaseViewModel() {
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
                val token = resp.data?.token.orEmpty()
//                val uid = resp.data?.uid.orEmpty()

                if (token.isNotEmpty()) {
                    userPreferencesRepository.saveLoginInfo(token, "uid")
                }

                _uiState.value = _uiState.value.copy(isLoading = false, loginSuccess = true)
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