package auth.domain.useCase

import auth.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend fun execute(token: String): Boolean {
        return authRepository.logout(token)
    }
}