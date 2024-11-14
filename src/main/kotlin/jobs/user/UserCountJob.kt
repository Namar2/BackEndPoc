package org.invendiv.jobs.user

import org.invendiv.domain.repository.user.UserRepository
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory

/**
 * UserCountJob is a continuous, long-running background job that periodically logs the current
 * number of users in the system. This job is intended to run at regular intervals throughout
 * the application's lifetime, using a loop to repeat the task.
 *
 * It utilizes a Kotlin `Job` to manage the life cycle of the background task, allowing
 * for controlled starting and stopping of the job.
 *
 * @property userRepository The UserRepository used to fetch users from the database.
 */
class UserCountJob(private val userRepository: UserRepository) {

    // Logger instance to output information and errors to the console or log file.
    private val logger = LoggerFactory.getLogger(UserCountJob::class.java)

    // Job instance to manage the lifecycle of this long-running, periodic task.
    private var job: Job? = null

    /**
     * Starts the background job using a coroutine that repeatedly executes the user count check.
     * This job will continue to run in a loop, executing at specified intervals as long as it is active.
     */
    fun start() {
        // Launch a coroutine in the default dispatcher, suitable for CPU-intensive work.
        job = CoroutineScope(Dispatchers.Default).launch {
            // Infinite loop that will run as long as the coroutine scope is active
            while (isActive) {
                delay(10000) // Suspends the coroutine for 10 seconds between executions.

                try {
                    // Fetches the total number of users from the repository.
                    val userCount = userRepository.fetchAllUsers().size
                    // Logs the current user count.
                    logger.info("Current user count: $userCount")
                } catch (e: Exception) {
                    // Logs an error message if fetching users fails, capturing the exception details.
                    logger.error("Failed to fetch user count", e)
                }
            }
        }
    }

    /**
     * Stops the background job by cancelling the coroutine if it is running.
     * This should be called when the application shuts down to free resources.
     */
    fun stop() {
        // Cancels the coroutine job, stopping the periodic task gracefully.
        job?.cancel()
    }
}