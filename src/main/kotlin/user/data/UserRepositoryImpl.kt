package org.invendiv.user.data

import core.data.tables.Users
import org.invendiv.user.domain.model.NewUser
import org.invendiv.user.domain.model.User
import org.invendiv.user.domain.repository.UserRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {


    override suspend fun addUser(user: NewUser): User? {
        // Validate user input
        if (user.name.isBlank()) {
            throw IllegalArgumentException("Name cannot be empty")
        }
        if (!user.email.contains("@")) {
            throw IllegalArgumentException("Invalid email format")
        }

        return try {
            // Insert the new user and retrieve the generated ID
            var userId: Int? = null
            transaction {
                // Insert into the Users table and retrieve the generated ID using get()
                userId = Users
                    .insert {
                        it[name] = user.name
                        it[email] = user.email
                    }[Users.id]  // Get the generated ID after insertion
            }

            // Return the User object after insertion
            userId?.let { User(id = it, name = user.name, email = user.email) }
        } catch (e: Exception) {
            // Handle any exceptions during the insert
            println("Error inserting user: ${e.message}")
            null
        }
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