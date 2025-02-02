package org.devabdi.secservice.service.impl

import org.devabdi.secservice.dto.AddApplicationDTO
import org.devabdi.secservice.entites.Application
import org.devabdi.secservice.exceptions.application.AppNotFoundException
import org.devabdi.secservice.exceptions.user.UserAlreadyExistsException
import org.devabdi.secservice.repo.ApplicationRepository
import org.devabdi.secservice.service.ApplicationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ApplicationServiceImpl(private val applicationRepository: ApplicationRepository) : ApplicationService {
    override fun getApplicationById(id: Long): Application? {
        return applicationRepository.findById(id).orElseThrow {
            AppNotFoundException("The application with the '${id}' has not been found")
        }
    }

    override fun addNewApplicationName(addApplicationDTO: AddApplicationDTO): Application? {
        val application = Application(addApplicationDTO.name)
        applicationRepository.findByName(application.name)?.let {
            throw UserAlreadyExistsException("'${application.name}' application name already exists.")
        }
        return applicationRepository.save(application)
    }

}