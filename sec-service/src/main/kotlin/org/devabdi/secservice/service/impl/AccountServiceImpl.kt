package org.devabdi.secservice.service.impl

import org.devabdi.secservice.dto.AddRoleRequestDTO
import org.devabdi.secservice.dto.RoleUserFormDTO
import org.devabdi.secservice.dto.SignUpDTO
import org.devabdi.secservice.entites.Role
import org.devabdi.secservice.entites.User
import org.devabdi.secservice.exceptions.role.DuplicateRoleException
import org.devabdi.secservice.exceptions.role.RoleAlreadyExistsException
import org.devabdi.secservice.exceptions.role.RoleNotFoundException
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

    override fun getAllUsersActive(): List<User>? {
        return appUserRepository.findByIsActiveTrue();
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

    override fun addRoleToUser(roleUserFormDTO: RoleUserFormDTO) {
        val user = appUserRepository.findByUsername(roleUserFormDTO.username)
            ?: throw UserNotFoundException("The user '${roleUserFormDTO.username}' has not been found")

        val role = appRoleRepository.findByRoleName(roleUserFormDTO.rolename.uppercase())
            ?: throw RoleNotFoundException("The role '${roleUserFormDTO.rolename}' has not been found")

        if (user.roles.any { it.roleName == role.roleName }) {
            throw DuplicateRoleException("The user '${roleUserFormDTO.username}' has already the role '${roleUserFormDTO.rolename}' ")
        }

        user.roles.add(role)
    }

    override fun deleteUserById(id: Long) {
        appUserRepository.findById(id).orElseThrow {
            UserNotFoundException("The user with the '${id}' has not been found")
        }
        appUserRepository.deleteById(id)
    }

    override fun deleteRoleById(id: Long) {
        val role = appRoleRepository.findById(id).orElseThrow {
            RoleNotFoundException("The role with the '${id}' has not been found")
        }

        val usersWithRole = appUserRepository.findAllByRoleIdQuery(id)
        usersWithRole?.forEach { user ->
            user.roles.remove(role)
            appUserRepository.save(user)
        }

        appRoleRepository.deleteById(id)
    }

    override fun deleteUserRole(roleUserFormDTO: RoleUserFormDTO) {
        val role = appRoleRepository.findByRoleName(roleUserFormDTO.rolename)
            ?: throw RoleNotFoundException("The role '${roleUserFormDTO.rolename}' has not been found")

        val user = appUserRepository.findByUsername(roleUserFormDTO.username)
            ?: throw UserNotFoundException("The user '${roleUserFormDTO.username}' has not been found")

        if (user.roles.none { it.roleName == role.roleName }) {
            throw RoleNotFoundException("The user '${roleUserFormDTO.username}' does not have the role '${roleUserFormDTO.rolename}' to remove.")
        }

        val toRemove = mutableListOf<Role>()
        for (roleUser in user.roles) {
            if (roleUser.id == role.id) {
                toRemove.add(role)
                break
            }
        }

        user.roles.removeAll(toRemove)
        appUserRepository.save(user)
    }

}