package com.zhaojun.feature.home.ui.diary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zhaojun.common.ui.component.CommonEmptyView
import com.zhaojun.feature.home.data.local.SyncStatus
import com.zhaojun.feature.home.data.local.entity.DiaryDraftEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DiaryDraftList(
    drafts: List<DiaryDraftEntity>,
    onDraftClick: (DiaryDraftEntity) -> Unit,
    onDraftDelete: (DiaryDraftEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (drafts.isEmpty()) {
        CommonEmptyView(text = "暂无草稿")
        return
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = drafts,
            key = { it.id },
        ) { draft ->
            DiaryDraftListItem(
                draft = draft,
                onClick = { onDraftClick(draft) },
                onDelete = { onDraftDelete(draft) },
            )
        }
    }
}

@Composable
private fun DiaryDraftListItem(
    draft: DiaryDraftEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = draft.title.ifBlank { "无标题" },
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = draft.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = formatDraftTime(draft.updatedAt),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = draftStatusLabel(draft.syncStatus),
                        style = MaterialTheme.typography.labelSmall,
                        color = draftStatusColor(draft.syncStatus),
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "删除草稿",
                )
            }
        }
    }
}

private fun draftStatusLabel(status: SyncStatus): String = when (status) {
    SyncStatus.DRAFT -> "草稿"
    SyncStatus.FAILED -> "提交失败"
}

@Composable
private fun draftStatusColor(status: SyncStatus) = when (status) {
    SyncStatus.DRAFT -> MaterialTheme.colorScheme.primary
    SyncStatus.FAILED -> MaterialTheme.colorScheme.error
}

private fun formatDraftTime(timestamp: Long): String {
    val formatter = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
    return formatter.format(Date(timestamp))
}
