package com.zhaojun.feature.login.vm

import androidx.lifecycle.viewModelScope
import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.common.core.store.UserPreferencesRepository
import com.zhaojun.common.core.util.ToastUtil
import com.zhaojun.common.network.interceptor.DefaultTokenProvider
import com.zhaojun.common.network.model.ApiResult
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
    private val userPreferencesRepository: UserPreferencesRepository,
    private val tokenProvider: DefaultTokenProvider,
    private val toastUtil: ToastUtil
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
            when (val result = repository.login(state.account, state.password)) {

                is ApiResult.Success -> {
                    val token = result.data?.token.orEmpty()
                    // 登录成功
                    if (token.isNotEmpty()) {
                        tokenProvider.updateToken(token)
                        userPreferencesRepository.saveLoginInfo(token, "uid")
                    }
                    _uiState.value = _uiState.value.copy(isLoading = false, loginSuccess = true)
                }

                is ApiResult.Error -> {
                    toastUtil.show(result.message)
                    // 统一错误处理
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMsg = result.message
                    )
                }
            }
        }
    }
}