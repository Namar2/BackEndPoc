package org.invendiv.user.domain.repository

import org.invendiv.user.domain.model.User


interface UserRepository {
    suspend fun addUser(user: User): Boolean
    suspend fun fetchAllUsers(): List<User>
}