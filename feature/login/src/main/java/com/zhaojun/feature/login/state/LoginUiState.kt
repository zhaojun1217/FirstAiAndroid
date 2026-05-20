package com.zhaojun.feature.login.state

data class LoginUiState(
    val account: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
)
