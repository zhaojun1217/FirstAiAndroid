package com.zhaojun.feature.home.vm

import com.therouter.TheRouter
import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.feature.home.state.HomeUiState
import com.zhaojun.router.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/29
 */
class HomeViewModel : BaseViewModel() {
    private val userService = TheRouter.get(UserService::class.java)

    private val _uiState = MutableStateFlow(
        HomeUiState(
            title = "Home Page - ${userService?.getUserName() ?: "服务为空"}"
        )
    )

    val uiState = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }
}