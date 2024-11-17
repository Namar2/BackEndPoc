package core.data

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

class MongoClientProvider {

    private val connectionString = "mongodb://localhost:27017/"
    private val client: MongoClient

    init {
        try {
            client = MongoClients.create(connectionString)
        } catch (e: Exception) {
            throw IllegalStateException("Failed to create MongoClient: ${e.message}", e)
        }
    }

    private fun getDatabase(databaseName: String): MongoDatabase {
        return client.getDatabase(databaseName)
    }

    fun getCollection(databaseName: String = "backendpoc", collectionName: String): MongoCollection<Document> {
        return getDatabase(databaseName).getCollection(collectionName)
    }
}