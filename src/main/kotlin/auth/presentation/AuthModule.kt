package org.invendiv.auth.presentation

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import utils.extensions.isBlacklisted
import java.util.*

object AuthModule {
    private const val jwtSecret = "vendingishot"
    private const val jwtIssuer = "ktor.io"
    private const val jwtAudience = "ktorAudience"
    private const val expirationTimeInMillis = 3_600_000 // 1 hour
    const val jwtConfig = "auth-jwt"

    /**
     * Configures JWT authentication for the Ktor application.
     * This function installs the Authentication feature in the Ktor application and sets up a JWT authentication provider.
     * It validates JWT tokens based on a predefined secret, issuer, and audience.
     *
     * @param application The Ktor application instance to configure.
     */
    fun configureAuthentication(application: Application) {
        application.install(Authentication) {
            jwt(jwtConfig) {
                realm = jwtIssuer

                verifier(
                    JWT.require(Algorithm.HMAC256(jwtSecret))
                        .withAudience(jwtAudience)
                        .withIssuer(jwtIssuer)
                        .build()
                )

                validate { jwtCredential ->
                    val token = jwtCredential.jwtId

                    if (token?.isBlacklisted() == true) {
                        null
                    } else {
                        if (jwtCredential.payload.getClaim("username").asString().isNotEmpty()) {
                            JWTPrincipal(jwtCredential.payload)
                        } else null
                    }
                }
            }
        }
    }

    /**
     * Generates a JWT token for the specified username.
     * The token includes claims for the audience, issuer, and a "username" claim.
     * It also has an expiration time set to the configured duration.
     *
     * @param username The username to include in the token payload.
     * @return The generated JWT token as a string.
     */
    fun generateToken(username: String): String {
        return JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("username", username)
            .withJWTId(UUID.randomUUID().toString())
            .withExpiresAt(Date(System.currentTimeMillis() + expirationTimeInMillis))
            .sign(Algorithm.HMAC256(jwtSecret))
    }
}