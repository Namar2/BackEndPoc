package org.invendiv.user.jobs

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory

/**
 * UserActionJobHandler handles one-off core.jobs triggered by specific HTTP requests, such as logging
 * additional core.data or performing follow-up tasks after a user action (e.g., user creation).
 *
 * Unlike org.invendiv.user.jobs.UserCountJob, which is continuous and requires a managed life cycle, each job here is
 * a single-use task. It is launched as a coroutine directly in `Dispatchers.IO`, making it an
 * independent, non-blocking operation that completes once the action is processed.
 */
class UserActionJobHandler {

    // Logger instance to output information and errors to the console or log file.
    private val logger = LoggerFactory.getLogger(UserActionJobHandler::class.java)

    /**
     * Starts a one-off job to log user activity. This function is designed to run asynchronously
     * in the background, allowing the HTTP response to return immediately without waiting for the job.
     *
     * @param userId ID of the user related to the action.
     * @param action Description of the user action.
     */
    fun startLogUserActionJob(userId: Int, action: String) {
        // Launches a one-off coroutine in the IO dispatcher, suitable for non-blocking IO tasks.
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Simulate processing time (e.g., for a complex logging operation)
                delay(2000) // 2-second delay to simulate background processing

                // Log user action details without blocking the response to the client.
                logger.info("User action logged: User ID $userId performed action: $action")
            } catch (e: Exception) {
                // Logs an error if something goes wrong in the logging process.
                logger.error("Failed to log user action for User ID $userId", e)
            }
        }
    }
}