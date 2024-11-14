package org.invendiv

import LifecycleManager
import auth.data.AuthRepositoryImpl
import auth.domain.useCase.CheckTokenValidityUseCase
import auth.domain.useCase.LoginUseCase
import auth.domain.useCase.LogoutUseCase
import auth.presentation.routes.authRoutes
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import org.invendiv.auth.presentation.AuthModule
import org.invendiv.data.setupDatabase
import org.invendiv.presentation.user.routes.userRoutes
import org.invendiv.user.data.UserRepositoryImpl
import org.invendiv.user.jobs.UserCountJob
import org.invendiv.user.useCase.AddUserUseCase
import org.invendiv.user.useCase.FetchUsersUseCase

fun main() {
    embeddedServer(factory = Netty, port = 2303, module = Application::module).start(wait = true)
}

fun Application.module() {

    val userRepository = UserRepositoryImpl()
    val addUserUseCase = AddUserUseCase(userRepository)
    val fetchUsersUseCase = FetchUsersUseCase(userRepository)


    val authRepository = AuthRepositoryImpl()
    val loginUseCase = LoginUseCase(authRepository)
    val logoutUseCase = LogoutUseCase(authRepository)
    val checkTokenValidityUseCase = CheckTokenValidityUseCase(authRepository)


    val userCountJob = UserCountJob(userRepository)
    val lifecycleManager = LifecycleManager(this, listOf(userCountJob))

    AuthModule.configureAuthentication(this)

    setupDatabase()

    // Install Plugins
    install(ContentNegotiation) { json() }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }


    routing {
        staticResources("/", "static")
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        authRoutes(
            loginUseCase = loginUseCase,
            logoutUseCase = logoutUseCase,
            checkTokenValidityUseCase = checkTokenValidityUseCase
        )

        userRoutes(
            addUserUseCase = addUserUseCase,
            fetchUsersUseCase = fetchUsersUseCase
        )
    }
}