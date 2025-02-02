package org.devabdi.secservice.web

import jakarta.validation.Valid
import org.devabdi.secservice.dto.AddApplicationDTO
import org.devabdi.secservice.dto.FindByIdRequestDTO
import org.devabdi.secservice.entites.Application
import org.devabdi.secservice.service.ApplicationService
import org.devabdi.secservice.utils.SecConstants
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(SecConstants.API_SERVICE_PATH)
class ApplicationRestController(private val applicationService: ApplicationService) {

    @GetMapping(SecConstants.GET_APPLICATION_URI_BY_ID)
    fun getAppById(@RequestBody @Valid findByIdRequestDTO: FindByIdRequestDTO): Application? {
        return applicationService.getApplicationById(findByIdRequestDTO.id)
    }

    @PostMapping(SecConstants.SAVE_APPLICATION_URI)
    fun addApplicationName(@RequestBody @Valid addApplicationDTO: AddApplicationDTO): ResponseEntity<String> {
        applicationService.addNewApplicationName(addApplicationDTO)
        return ResponseEntity(
            "The application name '${addApplicationDTO.name}' has been successfully added !",
            HttpStatus.CREATED
        )
    }
}