package org.invendiv.data.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and


object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50).uniqueIndex() // Unique constraint to ensure name uniqueness
    val email = varchar("email", 100).uniqueIndex() // Unique constraint to ensure email uniqueness
    override val primaryKey = PrimaryKey(id)

    /* Additional validation rules using SQL constraints.
    You can define any validation or constraint here to enforce data integrity directly at the database level,
     reducing the need to repeat checks throughout the codebase.
    */
    init {
        check { name.isNotNull() and (name neq "") } // Ensures name is not null and not empty
        check { email like "%@%" } // Basic validation to check the presence of '@' in email
    }
}