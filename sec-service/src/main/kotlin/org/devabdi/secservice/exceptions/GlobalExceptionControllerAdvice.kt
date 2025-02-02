package org.devabdi.secservice.exceptions

import com.fasterxml.jackson.databind.ObjectMapper
import org.devabdi.secservice.config.JacksonConfig
import org.devabdi.secservice.dto.ErrorMessageDTO
import org.devabdi.secservice.exceptions.application.AppNotFoundException
import org.devabdi.secservice.exceptions.role.RoleAlreadyExistsException
import org.devabdi.secservice.exceptions.user.UserAlreadyExistsException
import org.devabdi.secservice.exceptions.user.UserNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.time.OffsetDateTime

@ControllerAdvice
class GlobalExceptionControllerAdvice {

    private val objectMapper = JacksonConfig().objectMapper()
    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionControllerAdvice::class.java)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException, request: WebRequest): ResponseEntity<String> {
        //val stackTraceElement = ex.stackTrace.firstOrNull()

        val errorMessageDto = ErrorMessageDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "Invalid argument",
            path = (request as ServletWebRequest).request.requestURI,
            timestamp = OffsetDateTime.now(),
            method = request.httpMethod.toString(),
            exceptionType = ex.javaClass.simpleName,
            userId = "",
            errorLocation = getErrorLocaltion(ex)
        )

        errorLog(errorMessageDto.exceptionType, errorMessageDto.toString())
        val jsonErrorMessage = objectMapper.writeValueAsString(errorMessageDto)
        return ResponseEntity(jsonErrorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException, request: WebRequest): ResponseEntity<String> {
        val errorMessageDto = ErrorMessageDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "Invalid username",
            path = (request as ServletWebRequest).request.requestURI,
            timestamp = OffsetDateTime.now(),
            method = request.httpMethod.toString(),
            exceptionType = ex.javaClass.simpleName,
            userId = "",
            errorLocation = getErrorLocaltion(ex)
        )

        errorLog(errorMessageDto.exceptionType, errorMessageDto.toString())
        val jsonErrorMessage = objectMapper.writeValueAsString(errorMessageDto)
        return ResponseEntity(jsonErrorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: UserNotFoundException, request: WebRequest): ResponseEntity<String> {
        val errorMessageDto = ErrorMessageDTO(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "Invalid username",
            path = (request as ServletWebRequest).request.requestURI,
            timestamp = OffsetDateTime.now(),
            method = request.httpMethod.toString(),
            exceptionType = ex.javaClass.simpleName,
            userId = "",
            errorLocation = getErrorLocaltion(ex)
        )

        errorLog(errorMessageDto.exceptionType, errorMessageDto.toString())
        val jsonErrorMessage = objectMapper.writeValueAsString(errorMessageDto)
        return ResponseEntity(jsonErrorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(RoleAlreadyExistsException::class)
    fun handleRoleAlreadyExistsException(ex: RoleAlreadyExistsException, request: WebRequest): ResponseEntity<String> {
        val errorMessageDto = ErrorMessageDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "Invalid rolename",
            path = (request as ServletWebRequest).request.requestURI,
            timestamp = OffsetDateTime.now(),
            method = request.httpMethod.toString(),
            exceptionType = ex.javaClass.simpleName,
            userId = "",
            errorLocation = getErrorLocaltion(ex)
        )

        errorLog(errorMessageDto.exceptionType, errorMessageDto.toString())
        val jsonErrorMessage = objectMapper.writeValueAsString(errorMessageDto)
        return ResponseEntity(jsonErrorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AppNotFoundException::class)
    fun handleAppNotFoundException(exception: AppNotFoundException, request: WebRequest): ResponseEntity<String> {
        val errorMessageDto = ErrorMessageDTO(
            status = HttpStatus.NOT_FOUND.value(),
            message = exception.message ?: "Invalid rolename",
            path = (request as ServletWebRequest).request.requestURI,
            timestamp = OffsetDateTime.now(),
            method = request.httpMethod.toString(),
            exceptionType = exception.javaClass.simpleName,
            userId = "",
            errorLocation = getErrorLocaltion(exception)
        )

        errorLog(errorMessageDto.exceptionType, errorMessageDto.toString())
        val jsonErrorMessage = objectMapper.writeValueAsString(errorMessageDto)
        return ResponseEntity(jsonErrorMessage, HttpStatus.NOT_FOUND)
    }

    private fun errorLog(exceptionType: String, errorDetails: String) {
        logger.info("{} occurred: {}", exceptionType, errorDetails)
    }

    private fun getErrorLocaltion(ex: Throwable): String? {
        return ex.stackTrace.firstOrNull()?.let {
            "${it.className}.${it.methodName}(${it.fileName}:${it.lineNumber})"
        }
    }
}