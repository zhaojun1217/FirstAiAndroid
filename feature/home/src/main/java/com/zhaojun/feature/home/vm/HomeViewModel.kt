package com.zhaojun.feature.home.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.therouter.TheRouter
import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.common.core.ext.AuthEventBus
import com.zhaojun.common.core.store.UserPreferencesDataStore
import com.zhaojun.common.core.store.UserPreferencesRepository
import com.zhaojun.common.core.util.ToastUtil
import com.zhaojun.common.core.util.UiEvent
import com.zhaojun.common.network.interceptor.DefaultTokenProvider
import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.feature.home.repository.HomeViewRepository
import com.zhaojun.feature.home.state.HomeUiState
import com.zhaojun.feature.home.ui.MainTab
import com.zhaojun.router.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeViewRepository,
    private val defaultTokenProvider: DefaultTokenProvider,
    private val userPreferencesRepository: UserPreferencesRepository,
    val authEventBus: AuthEventBus,
) : BaseViewModel() {
    val service = TheRouter.get(UserService::class.java)
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    var uiState111 by  mutableStateOf(HomeUiState())
        private set
    var test : Flow<HomeUiState> = flow{

    }
    val name by lazy{}


    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    fun getDiaryList() {
        val state = _uiState.value

        viewModelScope.launch {
            _uiState.value = state.copy(loading = true)
            when (val result = repository.getDiaryList(1)) {
                is ApiResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        loading = false, cardList = result.data?.lists
                            ?: emptyList()
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        loading = false,
                    )
                }
            }

        }
        viewModelScope.async {

        }

    }

    fun clearLoginInfo() {
        viewModelScope.launch {
            defaultTokenProvider.clearToken()
            userPreferencesRepository.clearLoginInfo()
        }
    }
}