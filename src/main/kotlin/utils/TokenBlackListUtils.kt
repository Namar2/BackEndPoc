package utils

object TokenBlackListUtils {
    private val blacklist = mutableSetOf<String>()

    fun addToken(jwtToken: String) {
        blacklist.add(jwtToken)
    }

    fun isTokenBlacklisted(jwtToken: String): Boolean {
        return blacklist.contains(jwtToken)
    }
}