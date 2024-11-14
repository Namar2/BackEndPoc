package auth.domain.useCase

import auth.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(token: String): Boolean {
        return authRepository.logout(token)
    }
}