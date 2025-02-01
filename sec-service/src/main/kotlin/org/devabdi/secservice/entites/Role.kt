package org.devabdi.secservice.entites

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.devabdi.secservice.utils.SecConstants

@Entity
@Table(name = "app_role", schema = SecConstants.SEC_SCHEMA)
class Role(
    roleName: String = ""
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var roleName: String = roleName.trim().uppercase()
        set(value) {
            field = value.trim().uppercase()
        }
}