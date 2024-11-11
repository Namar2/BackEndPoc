package org.invendiv

import adapters.UserRepositoryImpl
import adapters.userRoutes
import application.useCase.AddUserUseCase
import application.useCase.FetchUsersUseCase
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import jobs.UserCountJob
import kotlinx.coroutines.*
import org.invendiv.frameworks.db.initDatabase
import org.invendiv.frameworks.db.setupDatabase
import org.slf4j.LoggerFactory

/**
 * Main function to start the embedded Ktor server on port 8080.
 * - Uses Netty as the server engine.
 * - Calls the module function to configure the application with routing and database setup.
 */
fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

/**
 * Application module function to set up the database, install plugins, and define routes.
 */
fun Application.module() {
    val userRepository = UserRepositoryImpl()
    val addUserUseCase = AddUserUseCase(userRepository)
    val fetchUsersUseCase = FetchUsersUseCase(userRepository)

    initDatabase()
    setupDatabase()

    install(ContentNegotiation) { json() }


    // Initialize the UserCountJob
    val userCountJob = UserCountJob(userRepository)
    userCountJob.start()

    // Register shutdown hook to stop the job when the application stops
    environment.monitor.subscribe(ApplicationStopped) { userCountJob.stop() }

    // Enable CORS to allow requests from other origins
    install(CORS) {
        anyHost() // Allows requests from any host; adjust as needed for production
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Accept)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
    }




    routing {
        static("/static") {
            resources("static")
        }

        userRoutes(addUserUseCase, fetchUsersUseCase)
    }
}
