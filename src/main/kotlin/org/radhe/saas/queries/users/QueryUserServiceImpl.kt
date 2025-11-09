package org.radhe.saas.queries.users

import org.radhe.saas.db.repos.UserRepo
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class QueryUserServiceImpl(
    private val userRepo: UserRepo
) : QueryUserService {

    override fun accept(id: String): Optional<UserDetailsDTO> {
        return userRepo.findById(id.toLong())
            .map { UserDetailsDTO(
                id = it.id!!,
                email = it.email,
                mobile = it.mobile,
                name = it.name
            ) }
    }
}

