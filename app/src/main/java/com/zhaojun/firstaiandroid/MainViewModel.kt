package com.zhaojun.firstaiandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhaojun.common.core.store.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/26
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun checkLogin(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isLogin = userPreferencesRepository.isLoginFlow.first()
            onResult(isLogin)
        }
    }
}