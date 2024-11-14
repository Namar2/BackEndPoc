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

fun Route.authRoutes(
    loginUseCase: LoginUseCase,
    logoutUseCase: LogoutUseCase,
    checkTokenValidityUseCase: CheckTokenValidityUseCase
) {
    get("/api/check-token") {
        val token = call.request.headers["Authorization"]?.decodeToken()

        if (token == null) {
            call.respond(HttpStatusCode.Unauthorized, "Token not provided")
            return@get
        }

        val isValid = checkTokenValidityUseCase(token)
        if (!isValid) {
            call.respond(HttpStatusCode.Unauthorized, "Token is invalid or expired")
        } else {
            call.respond(HttpStatusCode.OK, "Token is valid")
        }
    }

    post("/api/login") {
        val loginRequest = call.receive<LoginRequest>()

        val token = loginUseCase(loginRequest.username, loginRequest.password)

        if (token != null) {
            call.respond(HttpStatusCode.OK, mapOf("token" to token))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }

    post("/api/logout") {
        val token = call.request.headers["Authorization"]?.decodeToken()

        if (token != null) {
            logoutUseCase(token)
            call.respond(HttpStatusCode.OK, "Logged out successfully.")
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid request")
        }
    }
}