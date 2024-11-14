package utils

object TokenBlackListUtils {
    private val blacklist = mutableSetOf<String>()

    // Adds jwtId to the blacklist
    fun addToken(jwtToken: String) {
        blacklist.add(jwtToken)
    }

    // Checks if a jwtId is blacklisted
    fun isTokenBlacklisted(jwtToken: String): Boolean {
        return blacklist.contains(jwtToken)
    }
}