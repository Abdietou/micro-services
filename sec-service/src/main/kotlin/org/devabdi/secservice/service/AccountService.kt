package org.devabdi.secservice.service

import org.devabdi.secservice.dto.AddRoleRequestDTO
import org.devabdi.secservice.dto.SignUpDTO
import org.devabdi.secservice.entites.Role
import org.devabdi.secservice.entites.User

interface AccountService {
    fun getUserById(id: Long): User?
    fun getUserByUsername(username: String): User?
    fun getAllUsers(): List<User>?
    fun addNewUser(signUpDto: SignUpDTO): User?
    fun addNewRole(addRoleRequestDto: AddRoleRequestDTO): Role?
}