package com.zhaojun.feature.home.ui.diary

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhaojun.common.ui.effect.CollectUiEvents
import com.zhaojun.feature.home.vm.DiaryUploadViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryUploadScreen(
    initialDraftId: Long? = null,
    viewModel: DiaryUploadViewModel = hiltViewModel(),
) {
    val activity = LocalActivity.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(initialDraftId) {
        viewModel.loadDraftIfNeeded(initialDraftId)
    }

    CollectUiEvents(
        uiEvent = viewModel.uiEvent,
        onFinishCurrentPage = { activity?.finish() },
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (uiState.showDraftList) "草稿箱" else "上传日记")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (uiState.showDraftList) {
                                viewModel.closeDraftList()
                            } else {
                                activity?.finish()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "返回",
                        )
                    }
                },
                actions = {
                    if (uiState.showDraftList) {
                        TextButton(onClick = viewModel::createNewDiary) {
                            Text("新建")
                        }
                    } else {
                        TextButton(onClick = viewModel::openDraftList) {
                            Text("草稿")
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        if (uiState.showDraftList) {
            DiaryDraftList(
                drafts = uiState.draftList,
                onDraftClick = viewModel::editDraft,
                onDraftDelete = viewModel::deleteDraft,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                DiaryUploadForm(
                    uiState = uiState,
                    onTitleChange = viewModel::onTitleChange,
                    onContentChange = viewModel::onContentChange,
                    onImageUrlChange = viewModel::onImageUrlChange,
                    onWeatherChange = viewModel::onWeatherChange,
                    modifier = Modifier.weight(1f),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    OutlinedButton(
                        onClick = viewModel::saveDraft,
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.isSubmitting,
                    ) {
                        Text("保存草稿")
                    }

                    Button(
                        onClick = viewModel::submit,
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.isSubmitting,
                    ) {
                        if (uiState.isSubmitting) {
                            CircularProgressIndicator()
                        } else {
                            Text("提交")
                        }
                    }
                }
            }
        }
    }
}
