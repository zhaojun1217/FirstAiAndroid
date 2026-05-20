package com.zhaojun.common.core.util

/** ViewModel 发出、由 UI 层消费的一次性副作用事件 */
sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data object NavigateToLogin : UiEvent()
    data object NavigateToHome : UiEvent()
}
