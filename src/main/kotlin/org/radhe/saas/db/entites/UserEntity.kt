package org.radhe.saas.db.entites

import jakarta.annotation.Generated
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
data class UserEntity(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long?=null,
    val name: String,
    val email: String,
    val mobile: String,
)