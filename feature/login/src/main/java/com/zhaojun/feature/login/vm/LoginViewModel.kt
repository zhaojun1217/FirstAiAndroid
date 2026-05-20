package com.zhaojun.feature.login.vm

import androidx.lifecycle.viewModelScope
import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.common.core.ext.AuthEventBus
import com.zhaojun.common.core.store.UserPreferencesRepository
import com.zhaojun.common.core.util.UiEvent
import com.zhaojun.common.network.interceptor.DefaultTokenProvider
import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.feature.login.repository.LoginRepository
import com.zhaojun.feature.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val tokenProvider: DefaultTokenProvider,
    private val authEventBus: AuthEventBus,
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
                    if (token.isBlank()) {
                        val message = "登录失败：未获取到 token"
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMsg = message
                        )
                        sendUiEvent(UiEvent.ShowToast(message))
                        return@launch
                    }
                    val uid = result.data?.resolvedUid(state.account).orEmpty()
                    tokenProvider.updateToken(token)
                    userPreferencesRepository.saveLoginInfo(token, uid)
                    authEventBus.resetExpiredFlag()
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    sendUiEvent(UiEvent.NavigateToHome)
                }

                is ApiResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMsg = result.message
                    )
                    sendUiEvent(UiEvent.ShowToast(result.message))
                }
            }
        }
    }
}
