package com.anahoret.authdemo.users.domain

enum class UserRole {
    USER,
    ADMIN;

    companion object {
        const val USER_VALUE = "ROLE_USER"
        const val ADMIN_VALUE = "ROLE_ADMIN"

        @JvmStatic
        val roleNameMap = mapOf(
            "ROLE_USER" to USER,
            "ROLE_ADMIN" to ADMIN
        )
    }
}
