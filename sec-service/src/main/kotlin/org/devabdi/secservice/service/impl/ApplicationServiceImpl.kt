package org.devabdi.secservice.service.impl

import org.devabdi.secservice.dto.AddApplicationDTO
import org.devabdi.secservice.dto.ApplicationUserFormDTO
import org.devabdi.secservice.entites.Application
import org.devabdi.secservice.exceptions.application.AppNotFoundException
import org.devabdi.secservice.exceptions.application.DuplicateAppException
import org.devabdi.secservice.exceptions.user.UserAlreadyExistsException
import org.devabdi.secservice.exceptions.user.UserNotFoundException
import org.devabdi.secservice.repo.AppUserRepository
import org.devabdi.secservice.repo.ApplicationRepository
import org.devabdi.secservice.service.ApplicationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ApplicationServiceImpl(
    private val applicationRepository: ApplicationRepository,
    private val appUserRepository: AppUserRepository
) : ApplicationService {
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

    override fun addAppToUser(applicationUserFormDTO: ApplicationUserFormDTO) {
        val user = appUserRepository.findByUsername(applicationUserFormDTO.username)
            ?: throw UserNotFoundException("The user '${applicationUserFormDTO.username}' has not been found")

        val application = applicationRepository.findByName(applicationUserFormDTO.applicationName)
            ?: throw AppNotFoundException("The application named '${applicationUserFormDTO.applicationName}' has not been found")

        if (user.applications.any { it.name == application.name }) {
            throw DuplicateAppException("The user '${applicationUserFormDTO.username}' has already been registred to the application '${applicationUserFormDTO.applicationName}' ")
        }

        user.applications.add(application)
    }

    override fun deleteAppById(id: Long) {
        val application = applicationRepository.findById(id).orElseThrow {
            AppNotFoundException("The application with the '${id}' has not been found")
        }

        val usersWithApp = appUserRepository.findAllByAppIdQuery(id)
        usersWithApp?.forEach { user ->
            user.applications.remove(application)
            appUserRepository.save(user)
        }

        applicationRepository.deleteById(id)
    }

    override fun deleteUserApp(applicationUserFormDTO: ApplicationUserFormDTO) {
        val app = applicationRepository.findByName(applicationUserFormDTO.applicationName)
            ?: throw AppNotFoundException("The application '${applicationUserFormDTO.applicationName}' has not been found")

        val user = appUserRepository.findByUsername(applicationUserFormDTO.username)
            ?: throw UserNotFoundException("The user '${applicationUserFormDTO.username}' has not been found")

        if (user.applications.none { it.name == app.name }) {
            throw AppNotFoundException("The user '${applicationUserFormDTO.username}' does not have the application '${applicationUserFormDTO.applicationName}' to remove.")
        }

        val toRemove = mutableListOf<Application>()
        for (appUser in user.applications) {
            if (appUser.id == app.id) {
                toRemove.add(app)
            }
        }

        user.applications.removeAll(toRemove)
        appUserRepository.save(user)
    }

}