package org.invendiv.user.domain.useCase

import org.invendiv.user.domain.repository.UserRepository
import org.invendiv.user.domain.model.NewUser
import org.invendiv.user.domain.model.User

class AddUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(user: NewUser): User? {
        return userRepository.addUser(user)
    }
}