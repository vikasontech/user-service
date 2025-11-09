package org.radhe.saas.registeruser

import org.radhe.saas.db.entites.UserEntity
import org.radhe.saas.db.repos.UserRepo
import org.springframework.stereotype.Service

@Service
class CreateUserServiceImpl(
    private val userRepo: UserRepo
) : CreateUserService {

    override fun createUser(request: CreateUserRequest): CreateUserResponse {
        val userEntity = UserEntity(
            name = request.name,
            email = request.email,
            mobile = request.mobile
        )
        val save = userRepo.save(userEntity)
        return CreateUserResponse(id = save.id!!)
    }
}

