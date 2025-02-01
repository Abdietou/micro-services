package org.devabdi.secservice

import org.devabdi.secservice.dto.SignUpDTO
import org.devabdi.secservice.entites.User

fun User.toSignUpDto() = SignUpDTO(
    password = this.password,
    username = this.username,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email
)

fun SignUpDTO.toUser() = User(
    password = this.password,
    username = this.username,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email
)