package auth.domain

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import java.util.*

class JwtProvider {
    private val jwtSecret = "vendingishot"
    val jwtIssuer = "ktor.io"
    private val jwtAudience = "ktorAudience"
    private val expirationTimeInMillis = 3_600_000 // 1 hour

    fun generateToken(username: String): String {
        return JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("username", username)
            .withJWTId(UUID.randomUUID().toString())
            .withExpiresAt(Date(System.currentTimeMillis() + expirationTimeInMillis))
            .sign(Algorithm.HMAC256(jwtSecret))
    }

    fun configureVerifier(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .build()
    }
}