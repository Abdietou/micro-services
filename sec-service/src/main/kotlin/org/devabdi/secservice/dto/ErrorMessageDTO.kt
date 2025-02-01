package org.devabdi.secservice.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.devabdi.secservice.config.JacksonConfig
import java.time.OffsetDateTime

data class ErrorMessageDTO(
    val status: Int,
    val message: String,
    val path: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    val timestamp: OffsetDateTime,
    val method: String,
    @field:JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val exceptionType: String,
    val userId: String?,
    @field:JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val errorLocation: String?
) {
    override fun toString(): String {
        val logMap = mapOf(
            "status" to status,
            "message" to message,
            "path" to path,
            "timestamp" to timestamp,
            "method" to method,
            "exceptionType" to exceptionType,
            "userId" to userId,
            "errorLocation" to errorLocation
        )
        val objectMapper = JacksonConfig().objectMapper()
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logMap)
    }
}