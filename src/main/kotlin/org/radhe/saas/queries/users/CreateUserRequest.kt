package org.radhe.saas.queries.users

data class CreateUserRequest(
    val name: String,
    val email: String,
    val mobile: String,
)
