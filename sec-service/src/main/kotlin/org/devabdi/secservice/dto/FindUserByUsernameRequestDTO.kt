package org.devabdi.secservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class FindUserByUsernameRequestDTO(
    @field:NotBlank(message = "The username can't be null")
    @JsonProperty("username")
    val username: String
)
