package core.jobs

import io.ktor.server.application.*
import org.slf4j.LoggerFactory

class LifecycleManager(
    application: Application,
    private val jobs: List<LifecycleJob>
) {

    private val logger = LoggerFactory.getLogger(LifecycleManager::class.java)
    private var isStopped = false

    init {
        application.environment.monitor.subscribe(ApplicationStarted) {
            onStart()
        }
        application.environment.monitor.subscribe(ApplicationStopPreparing) {
            onStop()
        }
    }

    private fun onStart() {
        logger.info("Application has started.")
        jobs.forEach { it.start() }
    }

    private fun onStop() {
        if (isStopped) return

        isStopped = true // Set flag to true to prevent re-entry
        logger.info("Application is stopping, stopping all core.jobs...")
        jobs.forEach { it.stop() }
        logger.info("All background core.jobs have been stopped.")
    }
}