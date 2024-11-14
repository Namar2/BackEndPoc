package org.invendiv.user.repository

import org.invendiv.user.domain.model.NewUser
import org.invendiv.user.domain.model.User

interface UserRepository {
    suspend fun addUser(user: NewUser): User?
    suspend fun fetchAllUsers(): List<User>
}