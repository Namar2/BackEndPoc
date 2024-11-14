package auth.domain.useCase

import auth.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): String? {
        return authRepository.login(username, password)
    }
}