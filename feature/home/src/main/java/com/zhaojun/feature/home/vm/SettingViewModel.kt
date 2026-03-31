package com.zhaojun.feature.home.vm

import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.common.core.util.ToastUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/31
 */
@HiltViewModel
class SettingViewModel @Inject constructor(
    val toastUtil: ToastUtil
): BaseViewModel() {

    fun showToast(){
        toastUtil.show("sss")
    }

}