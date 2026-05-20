package com.zhaojun.common.core.ext

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthEventBus @Inject constructor() {
    private val _authExpired = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val authExpired: SharedFlow<Unit> = _authExpired.asSharedFlow()

    private val expiredNotified = AtomicBoolean(false)

    fun postExpired() {
        if (expiredNotified.compareAndSet(false, true)) {
            _authExpired.tryEmit(Unit)
        }
    }

    fun resetExpiredFlag() {
        expiredNotified.set(false)
    }
}
