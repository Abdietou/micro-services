package org.devabdi.secservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AddRoleRequestDTO
    constructor(
        @field:NotBlank(message = "The role's name can't be null or blank")
        @field:Size(max = 15)
        val roleName: String = ""
    )
