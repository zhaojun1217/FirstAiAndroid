package com.zhaojun.firstaiandroid

sealed class NavigateState {
    object LOADING : NavigateState()
    object HOME : NavigateState()
    object LOGIN : NavigateState()
}