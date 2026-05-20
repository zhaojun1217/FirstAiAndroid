package com.zhaojun.firstaiandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhaojun.common.core.store.UserPreferencesRepository
import com.zhaojun.common.network.interceptor.DefaultTokenProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val defaultTokenProvider: DefaultTokenProvider,
) : ViewModel() {
    private val _navigateState = MutableStateFlow<NavigateState>(NavigateState.LOADING)
    val navigateState = _navigateState.asStateFlow()

    fun checkLogin() {
        viewModelScope.launch {
            val token = userPreferencesRepository.tokenFlow.first()
            val isLogin = userPreferencesRepository.isLoginFlow.first()
            defaultTokenProvider.updateToken(token)

            _navigateState.value = if (isLogin && token.isNotBlank()) {
                NavigateState.HOME
            } else {
                NavigateState.LOGIN
            }
        }
    }
}
