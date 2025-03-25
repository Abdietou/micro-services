package org.devabdi.secservice.service

import org.devabdi.secservice.security.UserSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailService(
    private val accountService: AccountService
) : UserDetailsService {

    override fun loadUserByUsername(email: String?): UserDetails {
        val user = accountService.loadByUsername(email)
            ?: throw UsernameNotFoundException("Account is not found")
        val userId = user.id ?: throw IllegalStateException("User is not found")

        return UserSecurity(
            userId,
            user.email,
            user.password,
            user.roles.map { SimpleGrantedAuthority(it.roleName) }.toMutableList()
        )
    }
}