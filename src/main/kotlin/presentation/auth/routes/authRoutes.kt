package presentation.auth.routes

import com.auth0.jwt.JWT
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.invendiv.presentation.auth.AuthModule
import org.invendiv.utils.extensions.decodeToken
import presentation.auth.model.LoginRequest
import utils.extensions.addToBlacklist
import utils.extensions.isBlacklisted

fun Route.authRoutes() {

    get("/api/check-token") {
        val token = call.request.headers["Authorization"]?.decodeToken()

        if (token == null) {
            call.respond(HttpStatusCode.Unauthorized, "Token not provided")
            return@get
        }

        if (token.isBlacklisted()) {
            call.respond(HttpStatusCode.Unauthorized, "Token is invalid or expired")
        } else {
            call.respond(HttpStatusCode.OK, "Token is valid")
        }
    }

    post("/api/login") {
        val loginRequest = call.receive<LoginRequest>()

        if (loginRequest.username == "invendiv" && loginRequest.password == "invendiv") {
            val token = AuthModule.generateToken(loginRequest.username)
            call.respond(HttpStatusCode.OK, mapOf("token" to token))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }

    post("/api/logout") {
        val jwtToken = call.request.headers["Authorization"]?.decodeToken()

        jwtToken?.addToBlacklist()
        call.respond(HttpStatusCode.OK, "Logged out successfully.")
    }
}