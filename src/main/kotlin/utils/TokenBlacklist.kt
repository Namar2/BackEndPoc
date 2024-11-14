package utils

object TokenBlacklist {
    private val blacklist = mutableSetOf<String>()

    // Adds jwtId to the blacklist
    fun addToken(jwtId: String) {
        blacklist.add(jwtId)
    }

    // Checks if a jwtId is blacklisted
    fun isTokenBlacklisted(jwtId: String): Boolean {
        return blacklist.contains(jwtId)
    }
}