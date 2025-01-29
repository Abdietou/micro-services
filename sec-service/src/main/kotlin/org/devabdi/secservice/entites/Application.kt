package org.devabdi.secservice.entites

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.devabdi.secservice.utils.SecConstants

@Entity
@Table(name = "application", schema = SecConstants.SEC_SCHEMA)
class Application(
    name: String = ""
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var name: String = name.trim().replace(" ", "_").uppercase()
        set(value) {
            field = value.trim().replace(" ", "_").uppercase()
        }
}