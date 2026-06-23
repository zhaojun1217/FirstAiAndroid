package com.zhaojun.feature.home.ui.diary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zhaojun.feature.home.state.DiaryUploadUiState
import com.zhaojun.feature.home.vm.DiaryUploadViewModel

@Composable
fun DiaryUploadForm(
    uiState: DiaryUploadUiState,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onImageUrlChange: (String) -> Unit,
    onWeatherChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            value = uiState.title,
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("标题") },
            isError = uiState.titleError != null,
            supportingText = uiState.titleError?.let { error -> { Text(error) } },
            singleLine = true,
        )

        OutlinedTextField(
            value = uiState.content,
            onValueChange = onContentChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("内容") },
            isError = uiState.contentError != null,
            supportingText = {
                if (uiState.contentError != null) {
                    Text(uiState.contentError)
                } else {
                    Text("${uiState.content.length}/${DiaryUploadViewModel.MAX_CONTENT_LENGTH}")
                }
            },
            minLines = 3,
            maxLines = 5,
        )

        OutlinedTextField(
            value = uiState.imageUrl,
            onValueChange = onImageUrlChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("图片 URL") },
            isError = uiState.imageUrlError != null,
            supportingText = uiState.imageUrlError?.let { error -> { Text(error) } },
            singleLine = true,
        )

        if (uiState.imageUrl.isNotBlank()) {
            AsyncImage(
                model = uiState.imageUrl.trim(),
                contentDescription = "图片预览",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop,
            )
        }

        OutlinedTextField(
            value = uiState.weather,
            onValueChange = onWeatherChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("天气") },
            isError = uiState.weatherError != null,
            supportingText = uiState.weatherError?.let { error -> { Text(error) } },
            singleLine = true,
            placeholder = { Text("如：晴、多云") },
        )
    }
}
