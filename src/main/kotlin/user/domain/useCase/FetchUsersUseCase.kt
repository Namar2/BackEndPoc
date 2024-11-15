package org.invendiv.user.domain.useCase

import org.invendiv.user.domain.model.User
import org.invendiv.user.domain.repository.UserRepository
import org.koin.java.KoinJavaComponent.inject

class FetchUsersUseCase {
    private val userRepository: UserRepository by inject(UserRepository::class.java)

    suspend fun execute(): List<User> {
        return userRepository.fetchAllUsers()
    }
}