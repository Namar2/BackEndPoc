package org.invendiv.user.useCase

import org.invendiv.user.repository.UserRepository
import org.invendiv.user.domain.model.User

class FetchUsersUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): List<User> {
        return userRepository.fetchAllUsers()
    }
}