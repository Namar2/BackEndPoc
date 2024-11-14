package org.invendiv.user.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val name: String, val email: String)