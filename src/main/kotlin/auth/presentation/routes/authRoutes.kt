package auth.presentation.routes

import auth.domain.useCase.CheckTokenValidityUseCase
import auth.domain.useCase.LoginUseCase
import auth.domain.useCase.LogoutUseCase
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.invendiv.auth.domain.model.LoginRequest
import org.invendiv.utils.extensions.decodeToken
import org.koin.java.KoinJavaComponent.inject

fun Route.authRoutes() {
    val loginUseCase: LoginUseCase by inject(LoginUseCase::class.java)
    val logoutUseCase: LogoutUseCase by inject(LogoutUseCase::class.java)
    val checkTokenValidityUseCase: CheckTokenValidityUseCase by inject(CheckTokenValidityUseCase::class.java)

    post("/api/login") {
        val loginRequest = call.receive<LoginRequest>() // {"username": "invendiv", "password": "invendiv"}
        val token = loginUseCase.execute(loginRequest)
        if (token != null) {
            call.respond(HttpStatusCode.OK, mapOf("token" to token)) // {"token" : "$token"}
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }

    post("/api/logout") {
        val token = call.request.headers["Authorization"]?.decodeToken()
        if (token != null) {
            logoutUseCase.execute(token)
            call.respond(HttpStatusCode.OK, "Logged out successfully.")
        } else {
            call.respond(HttpStatusCode.BadRequest, "Token not provided")
        }
    }

    get("/api/check-token") {
        val token = call.request.headers["Authorization"]?.decodeToken()
        if (token != null && checkTokenValidityUseCase.execute(token)) {
            call.respond(HttpStatusCode.OK, "Token is valid")
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token is invalid or expired")
        }
    }
}