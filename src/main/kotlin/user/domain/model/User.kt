package org.invendiv.user.domain.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class User(
    val id: String = ObjectId().toHexString(),
    val name: String,
    val email: String
)