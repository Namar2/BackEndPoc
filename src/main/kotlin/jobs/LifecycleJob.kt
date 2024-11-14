package jobs

import kotlinx.coroutines.CoroutineScope

interface LifecycleJob {
    fun start()
    fun stop()
}