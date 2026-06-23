package com.zhaojun.feature.home.data.local

/**
 * 日记草稿同步状态。
 */
enum class SyncStatus {
    /** 用户主动保存的草稿 */
    DRAFT,

    /** 提交失败自动落库 */
    FAILED,
}
