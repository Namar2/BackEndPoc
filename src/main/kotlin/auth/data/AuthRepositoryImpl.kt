package auth.data

import auth.domain.repository.AuthRepository
import org.invendiv.auth.presentation.AuthModule
import utils.extensions.addToBlacklist
import utils.extensions.isBlacklisted

class AuthRepositoryImpl : AuthRepository {

    override suspend fun login(username: String, password: String): String? {

        return if (username == "invendiv" && password == "invendiv") {
            AuthModule.generateToken(username)
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