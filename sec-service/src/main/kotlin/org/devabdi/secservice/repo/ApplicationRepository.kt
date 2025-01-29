package org.devabdi.secservice.repo

import org.devabdi.secservice.entites.Application
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationRepository : JpaRepository<Application, Long> {

    fun findByName(name: String): Application?
}