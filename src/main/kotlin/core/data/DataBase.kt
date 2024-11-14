package core.data

import core.data.tables.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun initDatabase() {
    Database.connect(
        "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
        driver = "org.h2.Driver",
        user = "root",
        password = ""
    )
}

fun setupDatabase() {
    initDatabase()
    transaction {
        SchemaUtils.create(Users)

        /*
        // Uncomment this block to insert sample core.data into the Users table
        Users.insert {
            it[name] = "Netanel Amar"      // Example name for sample core.data
            it[email] = "NetanelCA2@gmail.com" // Example email for sample core.data
        }
        */
    }
}