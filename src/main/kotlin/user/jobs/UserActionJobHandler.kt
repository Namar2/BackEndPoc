package org.invendiv.user.jobs

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory

class UserActionJobHandler {

    private val logger = LoggerFactory.getLogger(UserActionJobHandler::class.java)

    private val supervisorJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + supervisorJob)

    private val anotherSupervisorJob = SupervisorJob()
    private val anotherCoroutineScope = CoroutineScope(Dispatchers.IO + anotherSupervisorJob)

    fun startLogUserActionJob(userId: Int, action: String) {
        // Launches a new coroutine to log the user action
        coroutineScope.launch {
            try {
                delay(2_000) // Simulating work
                logger.info("User action logged: User ID $userId performed action: $action")
            } catch (e: Exception) {
                logger.error("Failed to log user action for User ID $userId", e)
            }
        }
    }

    fun stop() {
        supervisorJob.cancel()
    }

    fun anotherStartLogUserActionJob(userId: Int, action: String) {
        anotherCoroutineScope.launch {
            try {
                delay(2_000) // Simulating work
                logger.info("User action logged: User ID $userId performed action: $action")
            } catch (e: Exception) {
                logger.error("Failed to log user action for User ID $userId", e)
            }
        }
    }

    fun anotherStop() {
        anotherSupervisorJob.cancel()
    }
}