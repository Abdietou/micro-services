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
    fun findByIsActiveTrue(): List<User>

    @Query(
        "SELECT * FROM ${SecConstants.SEC_USER_SCHEMA_TABLE} u JOIN ${SecConstants.SEC_USER_ROLES_SCHEMA_NAME_TABLE} r on u.id = r.app_user_id WHERE r.app_role_id = :roleId",
        nativeQuery = true
    )
    fun findAllByRoleIdQuery(roleId: Long): MutableList<User>?

    @Query(
        "SELECT * FROM ${SecConstants.SEC_USER_SCHEMA_TABLE} u JOIN ${SecConstants.SEC_USER_APPS_SCHEMA_NAME_TABLE} aua on u.id = aua.app_user_id WHERE aua.app_id = :appId",
        nativeQuery = true
    )
    fun findAllByAppIdQuery(appId: Long): MutableList<User>?
}