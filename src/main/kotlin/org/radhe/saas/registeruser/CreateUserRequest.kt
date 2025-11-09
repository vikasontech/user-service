package org.radhe.saas.registeruser

data class CreateUserRequest(
    val name: String,
    val email: String,
    val mobile: String,
)
