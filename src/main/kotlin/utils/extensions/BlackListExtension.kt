package utils.extensions

import com.auth0.jwt.JWT
import utils.TokenBlackListUtils

fun String.addToBlacklist() {
    TokenBlackListUtils.addToken(this)
}

fun String.isBlacklisted(): Boolean {
    return TokenBlackListUtils.isTokenBlacklisted(this)
}
fun String.getExpirationTime(): Long? {
    return try {
        JWT.decode(this).expiresAt?.time
    } catch (e: Exception) {
        null
    }
}