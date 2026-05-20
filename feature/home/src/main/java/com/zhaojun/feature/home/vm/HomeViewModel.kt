package com.zhaojun.feature.home.vm

import androidx.lifecycle.viewModelScope
import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.common.core.ext.AuthEventBus
import com.zhaojun.common.core.util.UiEvent
import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.common.network.session.SessionManager
import com.zhaojun.feature.home.repository.HomeViewRepository
import com.zhaojun.feature.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeViewRepository,
    private val sessionManager: SessionManager,
    authEventBus: AuthEventBus,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authEventBus.authExpired.collect {
                handleSessionExpired()
            }
        }
    }

    fun onMainTabSelected(index: Int) {
        _uiState.update { it.copy(mainTabIndex = index) }
    }

    fun onHomeFeedTabSelected(index: Int) {
        _uiState.update { it.copy(homeFeedTabIndex = index) }
    }

    fun getDiaryList(page: Int = 1) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            when (val result = repository.getDiaryList(page)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            cardList = result.data?.lists ?: emptyList()
                        )
                    }
                }

                is ApiResult.Error -> {
                    if (result.code != 401) {
                        sendUiEvent(UiEvent.ShowToast(result.message))
                    }
                    _uiState.update { it.copy(loading = false) }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            handleSessionExpired()
        }
    }

    private suspend fun handleSessionExpired() {
        sessionManager.clearSession()
        sendUiEvent(UiEvent.NavigateToLogin)
    }
}
