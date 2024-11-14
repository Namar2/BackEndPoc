package org.invendiv.domain.repository.user

import org.invendiv.domain.model.user.NewUser
import org.invendiv.domain.model.user.User

interface UserRepository {
    suspend fun addUser(user: NewUser): User?
    suspend fun fetchAllUsers(): List<User>
}