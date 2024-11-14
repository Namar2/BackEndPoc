package org.invendiv.utils.extensions

import com.auth0.jwt.JWT

fun String.decodeToken(): String? {
    return this.removePrefix("Bearer ")
        .let { JWT.decode(it).id }
}