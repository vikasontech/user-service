package org.radhe.saas.queries.users

data class UserDetailsDTO(
    val id: Long,
    val name: String,
    val email: String,
    val mobile: String,
)
