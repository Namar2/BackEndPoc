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
        * Equal to:

          CREATE TABLE IF NOT EXISTS Users (
              id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(50),
                email VARCHAR(100)
            );

        */
    }
}