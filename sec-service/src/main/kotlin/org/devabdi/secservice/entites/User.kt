package org.devabdi.secservice.entites

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.devabdi.secservice.utils.SecConstants
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.Date


@Entity
@Table(name = "app_user")
class User(

    @field:JsonProperty(access = JsonProperty.Access.WRITE_ONLY) var password: String = "",

    username: String = "",

    firstName: String = "",

    lastName: String = "",

    var email: String = ""
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var username: String = username.trim().lowercase()
        set(value) {
            field = value.trim().lowercase()
        }

    var isActive: Boolean = true

    @CreationTimestamp
    val createdAt: Date? = null

    @UpdateTimestamp
    val updatedAt: Date? = null

    var firstName: String = trimAndUppercase(firstName)
        set(value) {
            field = trimAndUppercase(value)
        }

    var lastName: String = trimAndUppercase(lastName)
        set(value) {
            field = trimAndUppercase(value)
        }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = SecConstants.SEC_USER_ROLES_NAME,
        joinColumns = [JoinColumn(name = "app_user_id")],
        inverseJoinColumns = [JoinColumn(name = "app_role_id")]
    )
    var roles: MutableList<Role> = mutableListOf()

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = SecConstants.SEC_USER_APPS_NAME,
        joinColumns = [JoinColumn(name = "app_user_id")],
        inverseJoinColumns = [JoinColumn(name = "app_id")]
    )
    var applications: MutableList<Application> = mutableListOf()

    private fun trimAndUppercase(value: String): String = value.trim().replaceFirstChar { it.uppercaseChar() }
}