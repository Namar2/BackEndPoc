package org.invendiv.user.data

import core.data.MongoClientProvider
import org.bson.Document
import org.bson.types.ObjectId
import org.invendiv.user.domain.model.User

import org.invendiv.user.domain.repository.UserRepository
import org.koin.java.KoinJavaComponent.inject

class UserRepositoryImpl : UserRepository {

    private val mongoClientProvider: MongoClientProvider by inject(MongoClientProvider::class.java)

    private val collectionName = "users"


    override suspend fun addUser(user: User): Boolean {

        if (user.name.isBlank() || !user.email.contains("@")) {
            throw IllegalArgumentException("Invalid user details")
        }

        return try {
            val document = Document("name", user.name)
                .append("email", user.email)

            val collection = mongoClientProvider.getCollection(collectionName = collectionName)

            val result = collection.insertOne(document)

            result.wasAcknowledged()
        } catch (e: Exception) {
            println("Error inserting user: ${e.message}")
            false
        }
    }


    override suspend fun fetchAllUsers(): List<User> {
        return try {
            val collection = mongoClientProvider.getCollection(collectionName = collectionName)

            collection.find().map { doc ->
                User(
                    id = (doc["_id"] as? ObjectId)?.toHexString() ?: "",
                    name = doc["name"] as? String ?: "Unknown",
                    email = doc["email"] as? String ?: "Unknown"
                )
            }.toList()
        } catch (e: Exception) {
            println("Error fetching users: ${e.message}")
            emptyList()
        }
    }
}