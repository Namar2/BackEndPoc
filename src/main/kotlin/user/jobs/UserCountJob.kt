package org.invendiv.user.jobs

import core.jobs.LifecycleJob
import kotlinx.coroutines.*
import org.invendiv.user.domain.repository.UserRepository
import org.slf4j.LoggerFactory

class UserCountJob(private val userRepository: UserRepository) : LifecycleJob {

    private val logger = LoggerFactory.getLogger(UserCountJob::class.java)

    // Dedicated scope and dispatcher
    private val jobScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun start() {
        jobScope.launch {
            while (isActive) {
                delay(5_000) // Every 5 seconds
                try {
                    val userCount = userRepository.fetchAllUsers().size
                    logger.info("Current user count: $userCount")
                } catch (e: Exception) {
                    logger.error("Failed to fetch user count", e)
                }
            }
        }
    }

    override fun stop() {
        jobScope.cancel() // Cancel the entire scope
        logger.info("org.invendiv.user.jobs.UserCountJob stopped.")
    }
}