package com.zhaojun.feature.home.ui.setting

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhaojun.feature.home.vm.SettingViewModel
import com.zhaojun.router.service.Navigator
import com.zhaojun.common.ui.R
import com.zhaojun.common.ui.component.CommonLoading

/**
 *     author : zhaojun
 *     e-mail : 1334561398@qq.com
 *     time   : 2026/03/31
 */

@Composable
fun SettingPage(vm: SettingViewModel = hiltViewModel()) {
    var darkModeEnabled by rememberSaveable { mutableStateOf(false) }
    var notificationEnabled by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current
    val testStr = stringResource(R.string.common_confirm)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            UserInfoCard(
                userName = "赵军",
                phone = "已登录账号：133****1398"
            )
        }
        item {
            SettingGroup(
                title = "通用设置"
            ) {
                SettingSwitchItem(
                    title = "深色模式",
                    subtitle = "开启后使用深色主题",
                    icon = Icons.Default.Palette,
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it }
                )

                HorizontalDivider()

                SettingSwitchItem(
                    title = "消息通知",
                    subtitle = "接收系统通知与提醒",
                    icon = Icons.Default.Notifications,
                    checked = notificationEnabled,
                    onCheckedChange = { notificationEnabled = it }
                )

                HorizontalDivider()

                SettingActionItem(
                    title = "清除缓存",
                    subtitle = "释放本地缓存空间",
                    icon = Icons.Default.Storage,
                    onClick = {
                        // TODO 清除缓存
                        Toast.makeText(context, testStr, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
        item {
//            Image(
//                imageVector = Icons.Default.Storage,
//                contentDescription = "存储"
//            )

//            Image(
//                painter = painterResource(R.drawable.svg_logo),
//                contentDescription = "设置",
//                modifier = Modifier
//                    .size(80.dp)
//                    .wrapContentSize()
//            )
        }
        item {
            SettingGroup(
                title = "关于"
            ) {
                SettingActionItem(
                    title = "关于我们",
                    subtitle = "查看应用介绍",
                    icon = Icons.Default.Info,
                    onClick = {
                        // TODO 跳转关于页
                        Navigator.toMine()
                    }
                )

                HorizontalDivider()

                SettingInfoItem(
                    title = "当前版本",
                    value = "1.0.0"
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    // TODO 退出登录
//                    Toast.makeText(context, "退出登录", Toast.LENGTH_SHORT).show()

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "退出登录"
                )
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text("退出登录")
            }
        }
    }
}