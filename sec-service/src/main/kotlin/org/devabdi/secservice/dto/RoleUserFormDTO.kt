package org.devabdi.secservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class RoleUserFormDTO(
    @field:NotBlank(message = "The username can't be null or blank")
    @JsonProperty("username")
    val username: String,
    @field:NotBlank(message = "The rolename can't be null or blank")
    @JsonProperty("rolename")
    val rolename: String
)
