package org.invendiv.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val name: String, val email: String)