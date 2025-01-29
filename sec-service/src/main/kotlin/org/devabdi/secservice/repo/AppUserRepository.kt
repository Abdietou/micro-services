package org.devabdi.secservice.repo

import org.devabdi.secservice.entites.User
import org.devabdi.secservice.utils.SecConstants
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}