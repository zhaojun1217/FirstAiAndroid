import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.zhaojun.feature.home.ui.MainTab
import com.zhaojun.feature.home.ui.discover.DiscoverPage
import com.zhaojun.feature.home.ui.home.HomePage
import com.zhaojun.feature.home.ui.setting.SettingPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainScreen() {

    // ✅ 只存 Int，绝对不崩溃
    var currentTabIndex by rememberSaveable { mutableStateOf(0) }

    // ✅ 自动映射成 Tab（不用保存）
    val tabs = listOf(MainTab.Home, MainTab.Discover, MainTab.Setting)
    val currentTab = tabs[currentTabIndex]

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
                        selected = currentTabIndex == index,
                        onClick = {
                            currentTabIndex = index
                        },
                        label = { Text(tab.title) },
                        icon = { }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentTab) {
                MainTab.Home -> HomePage()
                MainTab.Discover -> DiscoverPage()
                MainTab.Setting -> SettingPage()
            }
        }
    }
}