package org.radhe.saas.registeruser

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateUserController(
    private val createUserService: CreateUserService
) {

    @PostMapping("/user")
    fun createUser(@RequestBody createUserRequest: CreateUserRequest): ResponseEntity<CreateUserService?> {
        val createUser = createUserService.createUser(createUserRequest)
        return ResponseEntity.ok(createUserService)
    }
}