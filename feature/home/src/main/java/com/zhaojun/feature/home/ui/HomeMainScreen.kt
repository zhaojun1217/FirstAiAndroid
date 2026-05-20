package com.zhaojun.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhaojun.common.ui.effect.CollectUiEvents
import com.zhaojun.feature.home.ui.discover.DiscoverPage
import com.zhaojun.feature.home.ui.home.HomePage
import com.zhaojun.feature.home.ui.setting.SettingPage
import com.zhaojun.feature.home.vm.HomeViewModel
import com.zhaojun.router.service.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainScreen(
    vm: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    val tabs = listOf(MainTab.Home, MainTab.Discover, MainTab.Setting)
    val currentTab = tabs.getOrElse(uiState.mainTabIndex) { MainTab.Home }

    CollectUiEvents(
        uiEvent = vm.uiEvent,
        onNavigateToLogin = { Navigator.toLogin(context, clearTask = true) },
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentTab.title) }
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = uiState.mainTabIndex == index,
                        onClick = { vm.onMainTabSelected(index) },
                        label = { Text(tab.title) },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentTab) {
                MainTab.Home -> HomePage(vm = vm)
                MainTab.Discover -> DiscoverPage()
                MainTab.Setting -> SettingPage(vm = vm)
            }
        }
    }
}
