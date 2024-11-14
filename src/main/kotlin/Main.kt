package org.invendiv

import LifecycleManager
import UserCountJob
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import org.invendiv.data.initDatabase
import org.invendiv.data.setupDatabase
import org.invendiv.domain.repository.user.UserRepositoryImpl
import org.invendiv.domain.useCase.user.AddUserUseCase
import org.invendiv.domain.useCase.user.FetchUsersUseCase
import org.invendiv.presentation.auth.AuthModule
import org.invendiv.presentation.user.routes.userRoutes
import presentation.auth.routes.authRoutes

/**
 * Main function to start the embedded Ktor server on port 8080.
 * - Uses Netty as the server engine.
 * - Calls the module function to configure the application with routing and database setup.
 */
fun main() {
    embeddedServer(factory = Netty, port = 2303, module = Application::module).start(wait = true)
}

/**
 * Application module function to set up the database, install plugins, and define routes.
 */
fun Application.module() {
    val userRepository = UserRepositoryImpl()
    val addUserUseCase = AddUserUseCase(userRepository)
    val fetchUsersUseCase = FetchUsersUseCase(userRepository)

    val userCountJob = UserCountJob(userRepository)

    val lifecycleManager = LifecycleManager(this, listOf(userCountJob))

    initDatabase()
    setupDatabase()

    install(ContentNegotiation) { json() }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
    }


    AuthModule.configureAuthentication(this)

    routing {

        authRoutes() // Access to /api/login

        staticResources("/", "static")

        userRoutes(addUserUseCase, fetchUsersUseCase)
    }
}
