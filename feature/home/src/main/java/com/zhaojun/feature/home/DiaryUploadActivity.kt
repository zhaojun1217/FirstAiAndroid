package com.zhaojun.feature.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.therouter.router.Route
import com.zhaojun.common.ui.theme.FirstAiTheme
import com.zhaojun.feature.home.ui.diary.DiaryUploadScreen
import com.zhaojun.router.RouterPath
import dagger.hilt.android.AndroidEntryPoint

/** 上传日记 / 草稿编辑页 */
@AndroidEntryPoint
@Route(path = RouterPath.DIARY_UPLOAD)
class DiaryUploadActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val draftId = intent.getLongExtra(RouterPath.EXTRA_DRAFT_ID, INVALID_DRAFT_ID)
            .takeIf { it != INVALID_DRAFT_ID }

        setContent {
            FirstAiTheme {
                DiaryUploadScreen(initialDraftId = draftId)
            }
        }
    }

    companion object {
        private const val INVALID_DRAFT_ID = -1L
    }
}
