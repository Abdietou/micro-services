package org.devabdi.secservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class FindByIdRequestDTO(
    @field:NotNull
    @JsonProperty("id")
    val id: Long
)
