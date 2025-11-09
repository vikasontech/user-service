package org.radhe.saas.registeruser

interface CreateUserService {
    public fun createUser(request: CreateUserRequest):CreateUserResponse
}