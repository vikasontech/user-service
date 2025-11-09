package org.radhe.saas.queries.users

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class QueryUserInformationController(
    private val queryUserService: QueryUserService
) {

    @GetMapping("/user")
    fun accept(@PathVariable id: String): ResponseEntity<UserDetailsDTO?> {
        val userDetailDTO= queryUserService.accept(id)
        return userDetailDTO.map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).build());
    }
}