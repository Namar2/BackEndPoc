package org.invendiv.domain.repository.user

import org.invendiv.data.tables.Users
import org.invendiv.domain.model.user.NewUser
import org.invendiv.domain.model.user.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {

    override suspend fun addUser(user: NewUser): User? {
        var userId: Int? = null
        transaction {
            userId = Users.insert {
                it[name] = user.name
                it[email] = user.email
            } get Users.id
        }
        return userId?.let { User(id = it, name = user.name, email = user.email) }
    }

    override suspend fun fetchAllUsers(): List<User> = transaction {
        Users.selectAll().map { toUser(it) }
    }

    private fun toUser(row: ResultRow): User {
        return User(
            id = row[Users.id],
            name = row[Users.name],
            email = row[Users.email]
        )
    }
}