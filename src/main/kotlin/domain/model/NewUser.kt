package org.invendiv.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NewUser(val name: String, val email: String)