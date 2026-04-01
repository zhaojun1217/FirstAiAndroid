import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhaojun.firstaiandroid.MainViewModel
import com.zhaojun.firstaiandroid.NavigateState
import com.zhaojun.router.service.Navigator

@Composable
fun MainContentView(
    vm: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val navState by vm.navigateState.collectAsStateWithLifecycle()

    // 页面进入时检查登录状态
    LaunchedEffect(Unit) {
        vm.checkLogin()
    }

    // 统一处理导航副作用
    LaunchedEffect(navState) {
        when (navState) {
            NavigateState.HOME -> {
                Navigator.toHome(context, clearTask = true)
            }

            NavigateState.LOGIN -> {
                Navigator.toLogin(context, clearTask = true)
            }

            NavigateState.LOADING -> {
                // 不处理
            }
        }
    }

    // 只负责展示UI
    Box(modifier = Modifier.fillMaxSize()) {
        if (navState == NavigateState.LOADING) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}