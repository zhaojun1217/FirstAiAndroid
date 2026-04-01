package com.zhaojun.common.core.util

import android.os.Looper
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object IdleTaskDispatcher {

    fun postMain(tag: String = "IdleTask", task: () -> Unit) {
        Looper.getMainLooper().queue.addIdleHandler {
            try {
                task()
            } catch (t: Throwable) {
                Log.e(tag, "Idle main task failed", t)
            }
            false
        }
    }

    fun postIo(
        scope: CoroutineScope,
        tag: String = "IdleTask",
        task: suspend () -> Unit
    ) {
        Looper.getMainLooper().queue.addIdleHandler {
            scope.launch(Dispatchers.IO) {
                try {
                    task()
                } catch (t: Throwable) {
                    Log.e(tag, "Idle io task failed", t)
                }
            }
            false
        }
    }
}