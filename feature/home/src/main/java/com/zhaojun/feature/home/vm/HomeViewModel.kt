package com.zhaojun.feature.home.vm

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.common.core.util.ToastUtil
import com.zhaojun.common.core.util.UiEvent
import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.feature.home.repository.HomeViewRepository
import com.zhaojun.feature.home.state.HomeUiState
import com.zhaojun.feature.home.ui.MainTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val toastUtil: ToastUtil,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

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

    }
}