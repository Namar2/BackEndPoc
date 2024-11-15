package core.data.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and


object Users : Table() {
    val id = integer("id").autoIncrement().uniqueIndex()
    val name = varchar("name", 50)
    val email = varchar("email", 100)
    override val primaryKey = PrimaryKey(id)
    
    init {
        check { name.isNotNull() and (name neq "") } // Equal to: WHERE name <> ''
        check { email like "%@%" } // Equal to: WHERE email LIKE '%@%'
    }
}