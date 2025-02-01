package org.devabdi.secservice.service.impl

import org.devabdi.secservice.dto.AddRoleRequestDTO
import org.devabdi.secservice.dto.SignUpDTO
import org.devabdi.secservice.entites.Role
import org.devabdi.secservice.entites.User
import org.devabdi.secservice.exceptions.role.RoleAlreadyExistsException
import org.devabdi.secservice.exceptions.user.UserAlreadyExistsException
import org.devabdi.secservice.exceptions.user.UserNotFoundException
import org.devabdi.secservice.repo.AppRoleRepository
import org.devabdi.secservice.repo.AppUserRepository
import org.devabdi.secservice.service.AccountService
import org.devabdi.secservice.toUser
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountServiceImpl(
    private val appUserRepository: AppUserRepository,
    private val appRoleRepository: AppRoleRepository
) : AccountService {

    override fun getUserById(id: Long): User? {
        return appUserRepository.findById(id).orElseThrow {
            UserNotFoundException("The user with the '${id}' has not been found")
        }
    }

    override fun getUserByUsername(username: String): User? {
        return appUserRepository.findByUsername(username)
            ?: throw UserNotFoundException("The user '${username}' has not been found")
    }

    override fun getAllUsers(): List<User>? {
        return appUserRepository.findAll()
    }

    override fun addNewUser(signUpDto: SignUpDTO): User? {
        appUserRepository.findByEmail(signUpDto.email)?.let {
            throw UserAlreadyExistsException("This account already exists. Please log in.")
        }
        appUserRepository.findByUsername(signUpDto.username)?.let {
            throw UserAlreadyExistsException("The username '${signUpDto.username}' already exists. Please log in.")
        }

        val createdUser = signUpDto.toUser()
        return appUserRepository.save(createdUser)
    }

    override fun addNewRole(addRoleRequestDto: AddRoleRequestDTO): Role? {
        val role = Role(addRoleRequestDto.roleName)
        appRoleRepository.findByRoleName(role.roleName)?.let {
            throw RoleAlreadyExistsException("The rolename '${role.roleName}' already exists.")
        }

        return appRoleRepository.save(role)
    }

}