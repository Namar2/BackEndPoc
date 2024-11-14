package utils.extensions

import com.auth0.jwt.JWT
import utils.TokenBlacklist

fun String.addToBlacklist() {
    TokenBlacklist.addToken(this)
}

fun String.isBlacklisted(): Boolean {
    return TokenBlacklist.isTokenBlacklisted(this)
}
fun String.getExpirationTime(): Long? {
    return try {
        JWT.decode(this).expiresAt?.time
    } catch (e: Exception) {
        null
    }
}