package auth.domain.useCase

import auth.domain.repository.AuthRepository
import org.invendiv.auth.domain.model.LoginRequest
import org.koin.java.KoinJavaComponent.inject

class LoginUseCase {
    private val authRepository: AuthRepository by inject(AuthRepository::class.java)

    suspend fun execute(loginRequest: LoginRequest): String? {
        return authRepository.login(loginRequest.username, loginRequest.password)
    }
}