package org.invendiv.domain.useCase.user

import org.invendiv.domain.repository.user.UserRepository
import org.invendiv.domain.model.user.NewUser
import org.invendiv.domain.model.user.User

class AddUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(user: NewUser): User? {
        return userRepository.addUser(user)
    }
}