package com.anahoret.authdemo.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthEntryPointJwt(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    companion object {
        const val UNAUTHORIZED_ERROR_MESSAGE = "FULL AUTHENTICATION IS REQUIRED TO ACCESS THIS RESOURCE"
    }

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authEx: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json;charset=UTF-8"
    }
}
