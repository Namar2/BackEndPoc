package application.useCase

import domain.repository.UserRepository
import org.invendiv.domain.model.NewUser
import org.invendiv.domain.model.User

class AddUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(user: NewUser): User? {
        return userRepository.addUser(user)
    }
}

class FetchUsersUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): List<User> {
        return userRepository.fetchAllUsers()
    }
}