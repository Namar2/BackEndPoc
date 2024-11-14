package auth.domain.useCase

import auth.domain.repository.AuthRepository
import org.invendiv.auth.domain.model.LoginRequest

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend fun execute(loginRequest: LoginRequest): String? {
        return authRepository.login(loginRequest.username, loginRequest.password)
    }
}