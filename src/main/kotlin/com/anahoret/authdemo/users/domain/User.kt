package com.anahoret.authdemo.users.domain

data class User(
    val id: Long,
    val email: String,
    val userName: String,
    val roles: List<UserRole>
)
