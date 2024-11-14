package auth.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String): String?
    suspend fun logout(token: String): Boolean
    suspend fun isTokenValid(token: String): Boolean
}