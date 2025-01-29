package org.devabdi.secservice.utils

class SecConstants {
    companion object {
        // URL
        private const val API_PATH = "/api"
        private const val API_VERSION = "/v1"
        const val API_SERVICE_PATH = "$API_PATH$API_VERSION"

        // ENDPOINT PATH
        const val GLOBAL_CONFIG_URI = "global/config"
        const val GET_USER_BY_USERNAME_URI = "account/user/by-username"
        const val GET_USER_BY_ID_URI = "account/user/by-id"
        const val GET_ALL_USERS_URI = "account/users"
        const val SAVE_USER_URI = "account/user/save"
        const val SAVE_ROLE_URI = "account/role/save"
        const val SAVE_APPLICATION_URI = "account/application/save"
        const val ADD_ROLE_TO_USER_URI = "account/user/addRole"
        const val ADD_APPLICATION_TO_USER_URI = "account/user/addApplication"

        // DATABASE
        const val SEC_SCHEMA = "sso"
        private const val SEC_USER_TABLE_NAME = "app_user"
        const val SEC_USER_SCHEMA_TABLE = "$SEC_SCHEMA.$SEC_USER_TABLE_NAME"
    }
}