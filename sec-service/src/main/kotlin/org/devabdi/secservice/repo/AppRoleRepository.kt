package org.devabdi.secservice.repo

import org.devabdi.secservice.entites.Role
import org.springframework.data.jpa.repository.JpaRepository

interface AppRoleRepository : JpaRepository<Role, Long> {

    fun findByRoleName(roleName: String): Role?
}