package auth.data

import auth.domain.JwtProvider
import auth.domain.repository.AuthRepository
import utils.extensions.addToBlacklist
import utils.extensions.isBlacklisted

class AuthRepositoryImpl(private val jwtProvider: JwtProvider) : AuthRepository {

    override suspend fun login(username: String, password: String): String? {
        return if (username == "invendiv" && password == "invendiv") {
            jwtProvider.generateToken(username)
        } else {
            null
        }
    }

    override suspend fun logout(token: String): Boolean {
        token.addToBlacklist()
        return true
    }

    override suspend fun isTokenValid(token: String): Boolean {
        return !token.isBlacklisted()
    }
}