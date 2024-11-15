package org.invendiv

import auth.di.authModule
import auth.domain.JwtProvider
import auth.domain.JwtProvider.Companion.authJWT
import auth.presentation.routes.authRoutes
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import core.data.setupDatabase
import core.jobs.LifecycleManager
import org.invendiv.user.jobs.UserCountJob
import org.invendiv.user.presentation.routes.userRoutes
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import user.di.userModule

// main
fun main() {
    embeddedServer(factory = Netty, port = 2303, module = Application::module).start(wait = true)
}


// Application extension function
fun Application.module() {
    val jwtProvider by inject<JwtProvider>(JwtProvider::class.java)
    val userCountJob: UserCountJob by inject(UserCountJob::class.java)

    // DI
    startKoin {
        modules(authModule, userModule)
    }

    // JWT
    install(Authentication) {
        jwt(authJWT) {
            realm = jwtProvider.jwtIssuer
            verifier(jwtProvider.configureVerifier())
            validate { credential ->
                if (credential.payload.getClaim("username").asString().isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }


    // Server side related installations
    install(ContentNegotiation) { json() }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }

    // Database
    setupDatabase()

    // Lifecycle manager + Independent job
    val jobs = listOf(userCountJob)
    LifecycleManager(this, jobs)


    routing {
        // Static
        staticResources("/", "static")

        // Swagger
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        // Authentication related
        authRoutes()

        // User related
        userRoutes()
    }
}