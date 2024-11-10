package domain.repository

import org.invendiv.domain.model.NewUser
import org.invendiv.domain.model.User

interface UserRepository {
    suspend fun addUser(user: NewUser): User
    suspend fun fetchAllUsers(): List<User>
}