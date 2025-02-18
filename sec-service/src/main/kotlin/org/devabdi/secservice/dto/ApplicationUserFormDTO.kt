package org.devabdi.secservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class ApplicationUserFormDTO(
    @field:NotBlank(message = "The field username can't be null or blank")
    @JsonProperty("username")
    val username: String,
    @field:NotBlank(message = "The field applicationName can't be null or blank")
    @JsonProperty("applicationName")
    val applicationName: String
)
