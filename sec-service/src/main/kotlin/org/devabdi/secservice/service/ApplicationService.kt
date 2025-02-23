package org.devabdi.secservice.service

import org.devabdi.secservice.dto.AddApplicationDTO
import org.devabdi.secservice.dto.ApplicationUserFormDTO
import org.devabdi.secservice.entites.Application

interface ApplicationService {
    fun getApplicationById(id: Long): Application?
    fun addNewApplicationName(addApplicationDTO: AddApplicationDTO): Application?
    fun addAppToUser(applicationUserFormDTO: ApplicationUserFormDTO)
    fun deleteAppById(id: Long)
    fun deleteUserApp(applicationUserFormDTO: ApplicationUserFormDTO)
}