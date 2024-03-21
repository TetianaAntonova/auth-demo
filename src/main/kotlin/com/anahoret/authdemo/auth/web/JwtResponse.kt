package com.anahoret.authdemo.auth.web

data class JwtResponse(
    val token: String,
    val type: String = "Bearer",
    val id: Long,
    val userEmail: String,
    val userName: String,
    val roles: List<String>
)
