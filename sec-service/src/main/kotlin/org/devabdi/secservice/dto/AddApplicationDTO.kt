package org.devabdi.secservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AddApplicationDTO
constructor(
    @field:NotBlank(message = "The application's name can't be null or blank")
    @field:Size(max = 15)
    val name: String = ""
)