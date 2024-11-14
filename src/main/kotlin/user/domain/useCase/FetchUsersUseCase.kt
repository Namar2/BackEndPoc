package org.invendiv.user.domain.useCase

import org.invendiv.user.domain.repository.UserRepository
import org.invendiv.user.domain.model.User

class FetchUsersUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): List<User> {
        return userRepository.fetchAllUsers()
    }
}