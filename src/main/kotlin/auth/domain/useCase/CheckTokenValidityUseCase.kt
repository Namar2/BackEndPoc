package auth.domain.useCase

import auth.domain.repository.AuthRepository

class CheckTokenValidityUseCase(private val authRepository: AuthRepository) {
    suspend fun execute(token: String): Boolean {
        return authRepository.isTokenValid(token)
    }
}