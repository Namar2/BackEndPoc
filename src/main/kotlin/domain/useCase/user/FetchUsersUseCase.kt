package org.invendiv.domain.useCase.user

import org.invendiv.domain.repository.user.UserRepository
import org.invendiv.domain.model.user.User

class FetchUsersUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): List<User> {
        return userRepository.fetchAllUsers()
    }
}