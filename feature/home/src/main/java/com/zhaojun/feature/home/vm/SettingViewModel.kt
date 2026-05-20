package com.zhaojun.feature.home.vm

import com.zhaojun.common.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/** 设置页专属逻辑可在此扩展；登出等会话操作由 [HomeViewModel] 统一处理 */
@HiltViewModel
class SettingViewModel @Inject constructor() : BaseViewModel()
