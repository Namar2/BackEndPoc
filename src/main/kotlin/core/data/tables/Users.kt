package core.data.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and


object Users : Table() {
    val id = integer("id").autoIncrement()
    // Unique constraint to ensure name uniqueness (this is why i cant enter 2 users with same name)
    val name = varchar("name", 50).uniqueIndex()
    // Unique constraint to ensure email uniqueness (this is why i cant enter 2 users with same email)
    val email = varchar("email", 100).uniqueIndex()
    override val primaryKey = PrimaryKey(id)


    init {
        check { name.isNotNull() and (name neq "") } // Ensures name is not null and not empty
        check { email like "%@%" } // Basic validation to check the presence of '@' in email
    }
}