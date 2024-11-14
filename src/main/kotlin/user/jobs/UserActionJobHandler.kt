package org.invendiv.user.jobs

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory


class UserActionJobHandler {

    // Logger instance to output information and errors to the console or log file.
    private val logger = LoggerFactory.getLogger(UserActionJobHandler::class.java)

    fun startLogUserActionJob(userId: Int, action: String) {
        // Launches a one-off coroutine in the IO dispatcher, suitable for non-blocking IO tasks.
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Simulate processing time
                delay(2_000)

                logger.info("User action logged: User ID $userId performed action: $action")
            } catch (e: Exception) {
                logger.error("Failed to log user action for User ID $userId", e)
            }
        }
    }
}