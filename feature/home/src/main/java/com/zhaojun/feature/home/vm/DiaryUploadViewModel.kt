package com.zhaojun.feature.home.vm

import androidx.lifecycle.viewModelScope
import com.zhaojun.common.core.base.BaseViewModel
import com.zhaojun.common.core.util.UiEvent
import com.zhaojun.common.network.model.ApiResult
import com.zhaojun.feature.home.data.local.SyncStatus
import com.zhaojun.feature.home.data.local.entity.DiaryDraftEntity
import com.zhaojun.feature.home.model.DiaryCreateRequest
import com.zhaojun.feature.home.repository.DiaryDraftRepository
import com.zhaojun.feature.home.repository.HomeViewRepository
import com.zhaojun.feature.home.state.DiaryUploadUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class DiaryUploadViewModel @Inject constructor(
    private val diaryDraftRepository: DiaryDraftRepository,
    private val homeViewRepository: HomeViewRepository,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(DiaryUploadUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeDraftList()
    }

    private fun observeDraftList() {
        viewModelScope.launch {
            diaryDraftRepository.observeAllDrafts().collect { drafts ->
                _uiState.update { it.copy(draftList = drafts) }
            }
        }
    }

    /** 从 Intent 传入的草稿 ID 加载已有内容 */
    fun loadDraftIfNeeded(draftId: Long?) {
        if (draftId == null) return
        viewModelScope.launch {
            val draft = diaryDraftRepository.getDraftById(draftId) ?: return@launch
            applyDraftToForm(draft)
        }
    }

    fun openDraftList() {
        _uiState.update { it.copy(showDraftList = true) }
    }

    fun closeDraftList() {
        _uiState.update { it.copy(showDraftList = false) }
    }

    /** 从草稿列表选中一项进入编辑 */
    fun editDraft(draft: DiaryDraftEntity) {
        applyDraftToForm(draft)
        _uiState.update { it.copy(showDraftList = false) }
    }

    /** 清空表单，开始新建日记 */
    fun createNewDiary() {
        _uiState.update {
            it.copy(
                draftId = null,
                title = "",
                content = "",
                imageUrl = "",
                weather = "",
                showDraftList = false,
                titleError = null,
                contentError = null,
                imageUrlError = null,
                weatherError = null,
            )
        }
    }

    fun deleteDraft(draft: DiaryDraftEntity) {
        viewModelScope.launch {
            diaryDraftRepository.deleteDraft(draft)
            if (_uiState.value.draftId == draft.id) {
                createNewDiary()
            }
            sendUiEvent(UiEvent.ShowToast("草稿已删除"))
        }
    }

    private fun applyDraftToForm(draft: DiaryDraftEntity) {
        _uiState.update {
            it.copy(
                draftId = draft.id,
                title = draft.title,
                content = draft.content,
                imageUrl = draft.imageUrl,
                weather = draft.weather,
                titleError = null,
                contentError = null,
                imageUrlError = null,
                weatherError = null,
            )
        }
    }

    fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title, titleError = null) }
    }

    fun onContentChange(content: String) {
        _uiState.update { it.copy(content = content, contentError = null) }
    }

    fun onImageUrlChange(imageUrl: String) {
        _uiState.update { it.copy(imageUrl = imageUrl, imageUrlError = null) }
    }

    fun onWeatherChange(weather: String) {
        _uiState.update { it.copy(weather = weather, weatherError = null) }
    }

    /** 保存草稿到 Room */
    fun saveDraft() {
        val currentState = _uiState.value
        if (!validateForSaveDraft(currentState)) return

        viewModelScope.launch {
            val draftEntity = buildDraftEntity(
                state = currentState,
                syncStatus = SyncStatus.DRAFT,
            )
            val savedDraftId = diaryDraftRepository.saveDraft(draftEntity)
            _uiState.update { it.copy(draftId = savedDraftId) }
            sendUiEvent(UiEvent.ShowToast("草稿已保存"))
        }
    }

    /** 提交日记到服务端，失败时以 FAILED 状态落库 */
    fun submit() {
        val currentState = _uiState.value
        if (!validateForSubmit(currentState)) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }

            val imageUrl = currentState.imageUrl.trim()
            val request = DiaryCreateRequest(
                title = currentState.title.trim(),
                content = currentState.content.trim(),
                img_urls = if (imageUrl.isEmpty()) emptyList() else listOf(imageUrl),
                weather = currentState.weather.trim(),
            )

            when (val result = homeViewRepository.createDiary(request)) {
                is ApiResult.Success -> {
                    deleteDraftIfExists(currentState.draftId)
                    _uiState.update { it.copy(isSubmitting = false) }
                    sendUiEvent(UiEvent.ShowToast("提交成功"))
                    sendUiEvent(UiEvent.FinishCurrentPage)
                }

                is ApiResult.Error -> {
                    val failedDraft = buildDraftEntity(
                        state = currentState,
                        syncStatus = SyncStatus.FAILED,
                        lastError = result.message,
                    )
                    val savedDraftId = diaryDraftRepository.saveDraft(failedDraft)
                    _uiState.update { it.copy(isSubmitting = false, draftId = savedDraftId) }
                    if (result.code != 401) {
                        sendUiEvent(UiEvent.ShowToast("提交失败，已保存到草稿"))
                    }
                }
            }
        }
    }

    private suspend fun buildDraftEntity(
        state: DiaryUploadUiState,
        syncStatus: SyncStatus,
        lastError: String? = null,
    ): DiaryDraftEntity {
        val now = System.currentTimeMillis()
        val existingDraft = state.draftId?.let { diaryDraftRepository.getDraftById(it) }
        return DiaryDraftEntity(
            id = state.draftId ?: 0,
            title = state.title.trim(),
            content = state.content.trim(),
            imageUrl = state.imageUrl.trim(),
            weather = state.weather.trim(),
            syncStatus = syncStatus,
            createdAt = existingDraft?.createdAt ?: now,
            updatedAt = now,
            lastError = lastError,
        )
    }

    private suspend fun deleteDraftIfExists(draftId: Long?) {
        if (draftId == null) return
        val draft = diaryDraftRepository.getDraftById(draftId) ?: return
        diaryDraftRepository.deleteDraft(draft)
    }

    private fun validateForSaveDraft(state: DiaryUploadUiState): Boolean {
        var isValid = true
        var titleError: String? = null
        var contentError: String? = null
        var weatherError: String? = null

        if (state.title.isBlank()) {
            titleError = "标题不能为空"
            isValid = false
        }
        if (state.content.isBlank()) {
            contentError = "内容不能为空"
            isValid = false
        } else if (state.content.length > MAX_CONTENT_LENGTH) {
            contentError = "内容不能超过${MAX_CONTENT_LENGTH}字"
            isValid = false
        }
        if (state.weather.isBlank()) {
            weatherError = "天气不能为空"
            isValid = false
        }

        _uiState.update {
            it.copy(
                titleError = titleError,
                contentError = contentError,
                weatherError = weatherError,
                imageUrlError = null,
            )
        }
        return isValid
    }

    private fun validateForSubmit(state: DiaryUploadUiState): Boolean {
        if (!validateForSaveDraft(state)) return false

        val imageUrl = state.imageUrl.trim()
        if (imageUrl.isNotEmpty() && !isValidImageUrl(imageUrl)) {
            _uiState.update { it.copy(imageUrlError = "请输入有效的图片 URL") }
            return false
        }
        return true
    }

    private fun isValidImageUrl(url: String): Boolean {
        return try {
            val uri = URI(url)
            uri.scheme in SUPPORTED_IMAGE_SCHEMES && !uri.host.isNullOrBlank()
        } catch (_: Exception) {
            false
        }
    }

    companion object {
        const val MAX_CONTENT_LENGTH = 100
        private val SUPPORTED_IMAGE_SCHEMES = setOf("http", "https")
    }
}
