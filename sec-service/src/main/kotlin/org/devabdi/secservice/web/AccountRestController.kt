package org.devabdi.secservice.web

import jakarta.validation.Valid
import org.devabdi.secservice.dto.AddRoleRequestDTO
import org.devabdi.secservice.dto.FindByIdRequestDTO
import org.devabdi.secservice.dto.FindUserByUsernameRequestDTO
import org.devabdi.secservice.dto.SignUpDTO
import org.devabdi.secservice.entites.User
import org.devabdi.secservice.service.AccountService
import org.devabdi.secservice.utils.SecConstants
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(SecConstants.API_SERVICE_PATH)
class AccountRestController(private val accountService: AccountService) {

    @GetMapping(SecConstants.GET_ALL_USERS_URI)
    fun getAllUsers(): List<User>? {
        return accountService.getAllUsers()
    }

    @GetMapping(SecConstants.GET_USER_BY_ID_URI)
    fun getUserById(@RequestBody @Valid findByIdRequestDTO: FindByIdRequestDTO): User? {
        return accountService.getUserById(findByIdRequestDTO.id)
    }

    @GetMapping(SecConstants.GET_USER_BY_USERNAME_URI)
    fun getUserByUsername(@RequestBody @Valid findUserByUsernameRequestDTO: FindUserByUsernameRequestDTO): User? {
        return accountService.getUserByUsername(findUserByUsernameRequestDTO.username)
    }

    @PostMapping(SecConstants.SAVE_USER_URI)
    fun addNewUser(@RequestBody @Valid signUpDto: SignUpDTO): ResponseEntity<String> {
        accountService.addNewUser(signUpDto)
        return ResponseEntity("The user '${signUpDto.username}' has been successfully added !", HttpStatus.CREATED)
    }

    @PostMapping(SecConstants.SAVE_ROLE_URI)
    fun addRole(@RequestBody @Valid addRoleRequestDTO: AddRoleRequestDTO): ResponseEntity<String> {
        accountService.addNewRole(addRoleRequestDTO)
        return ResponseEntity(
            "The role '${addRoleRequestDTO.roleName}' has been successfully added !",
            HttpStatus.CREATED
        )
    }

}