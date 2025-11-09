package org.radhe.saas.db.repos

import org.radhe.saas.db.entites.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepo: CrudRepository<UserEntity, Long>