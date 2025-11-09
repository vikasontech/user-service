package org.radhe.saas.queries.users

import java.util.Optional

interface QueryUserService {
    public fun accept(id: String): Optional<UserDetailsDTO>
}