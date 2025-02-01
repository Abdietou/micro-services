package org.devabdi.secservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignUpDTO
constructor(
    @field:NotBlank(message = "The password can't be null or blank")
    @field:Size(min = 4, max = 32, message = "The password must be between 4 and 32 characters")
    @field:NotEmpty(message = "The password can't be empty")
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&?])[A-Za-z\\d!@#\$%^&?]{4,}\$",
        message = "The password must contain at least one uppercase letter, one lowercase letter, one number, and one special character (!, @, #, $, %, ^, &, ?)."
    )
    val password: String = "",

    @field:NotBlank(message = "The username can't be null or blank")
    @field:Size(min = 4, max = 16, message = "The username must be between 4 and 16 characters")
    @field:NotEmpty(message = "The username can't be empty")
    val username: String = "",

    @field:NotBlank(message = "The First Name can't be null or blank")
    @field:NotEmpty(message = "The First Name  can't be empty")
    val firstName: String = "",

    @field:NotBlank(message = "The Last Name can't be null or blank")
    @field:NotEmpty(message = "The Last Name can't be empty")
    val lastName: String = "",

    @field:NotBlank(message = "The Email can't be null or blank")
    @field:NotEmpty(message = "The Email can't be empty")
    @field:Email(message = "The email must be a valid email")
    val email: String = ""
)