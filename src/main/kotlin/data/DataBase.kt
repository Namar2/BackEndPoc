package org.invendiv.data

import org.invendiv.data.tables.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Initializes the database connection.
 * Connects to an in-memory H2 database with the provided URL and credentials.
 *
 * - `jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;`: Creates an in-memory H2 database
 *   that will persist as long as the application is running. The `DB_CLOSE_DELAY=-1`
 *   parameter ensures that the database is not closed when the last connection is closed.
 * - `driver`: Specifies the H2 database driver.
 * - `user` and `password`: Credentials for accessing the database (set to "root" with an empty password for simplicity).
 */
fun initDatabase() {
    Database.connect(
        "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
        driver = "org.h2.Driver",
        user = "root",
        password = ""
    )
}

/**
 * Sets up the database schema and tables.
 * - Begins a transaction, ensuring that all operations are treated as a single unit.
 * - Creates the Users table if it does not exist, using Exposed's SchemaUtils.
 *
 * Sample data insertion code is commented out for demonstration purposes.
 * Uncomment it if you want to insert initial data into the Users table when setting up the database.
 */
fun setupDatabase() {
    initDatabase()
    transaction {
        // Create the Users table if it doesn't exist
        SchemaUtils.create(Users)

        /*
        // Uncomment this block to insert sample data into the Users table
        Users.insert {
            it[name] = "Netanel Amar"      // Example name for sample data
            it[email] = "NetanelCA2@gmail.com" // Example email for sample data
        }
        */
    }
}