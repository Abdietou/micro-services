package org.devabdi.secservice.exceptions.application

import org.devabdi.secservice.dto.ErrorMessageDTO
import org.devabdi.secservice.exceptions.GlobalExceptionControllerAdvice
import org.devabdi.secservice.utils.SecConstants
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.time.OffsetDateTime

@ControllerAdvice
class AppExceptionHandler: GlobalExceptionControllerAdvice() {

    @ExceptionHandler(DuplicateAppException::class)
    fun handleDuplicateAppException(ex: DuplicateAppException, request: WebRequest): ResponseEntity<String> {
        val servletRequest = request as? ServletWebRequest
        val httpServletRequest = servletRequest?.request

        val errorMessageDto = ErrorMessageDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: SecConstants.INVALID_APP_NAME,
            path = servletRequest?.request?.requestURI,
            timestamp = OffsetDateTime.now(),
            method = servletRequest?.httpMethod.toString(),
            exceptionType = ex.javaClass.simpleName,
            userId = "",
            errorLocation = getErrorLocaltion(ex),
            userAgent = servletRequest?.getHeader(SecConstants.USER_AGENT) ?: SecConstants.UNKNOWN,
            contentType = httpServletRequest?.contentType ?: SecConstants.UNKNOWN,
            serverName = httpServletRequest?.serverName ?: SecConstants.UNKNOWN,
            ip = httpServletRequest?.let { getClientIp(it) } ?: SecConstants.UNKNOWN
        )

        errorLog(errorMessageDto.exceptionType, errorMessageDto.toString())
        val jsonErrorMessage = objectMapper.writeValueAsString(errorMessageDto)
        return ResponseEntity(jsonErrorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AppNotFoundException::class)
    fun handleAppNotFoundException(exception: AppNotFoundException, request: WebRequest): ResponseEntity<String> {
        val servletRequest = request as? ServletWebRequest
        val httpServletRequest = servletRequest?.request

        val errorMessageDto = ErrorMessageDTO(
            status = HttpStatus.NOT_FOUND.value(),
            message = exception.message ?: SecConstants.INVALID_APP_NAME,
            path = servletRequest?.request?.requestURI,
            timestamp = OffsetDateTime.now(),
            method = servletRequest?.httpMethod.toString(),
            exceptionType = exception.javaClass.simpleName,
            userId = "",
            errorLocation = getErrorLocaltion(exception),
            userAgent = request.getHeader(SecConstants.USER_AGENT) ?: SecConstants.UNKNOWN,
            contentType = httpServletRequest?.contentType ?: SecConstants.UNKNOWN,
            serverName = httpServletRequest?.serverName ?: SecConstants.UNKNOWN,
            ip = httpServletRequest?.let { getClientIp(it) } ?: SecConstants.UNKNOWN
        )

        errorLog(errorMessageDto.exceptionType, errorMessageDto.toString())
        val jsonErrorMessage = objectMapper.writeValueAsString(errorMessageDto)
        return ResponseEntity(jsonErrorMessage, HttpStatus.NOT_FOUND)
    }
}