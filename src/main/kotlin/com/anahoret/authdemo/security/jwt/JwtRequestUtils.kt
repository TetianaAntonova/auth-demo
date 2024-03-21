package com.anahoret.authdemo.security.jwt

import jakarta.servlet.http.HttpServletRequest
import java.net.URLDecoder

fun HttpServletRequest.getToken(): String? {
    return getTokenFromHeader() ?: getTokenFromCookie()
}

private fun HttpServletRequest.getTokenFromHeader(): String? {
    return getHeader(JwtAuthorizationTokenFilter.JWT_TOKEN_HEADER_NAME)
        ?.let { URLDecoder.decode(it, "UTF-8") }
        ?.takeIf { it.startsWith("${JwtAuthorizationTokenFilter.JWT_TOKEN_HEADER_PREFIX} ") }
        ?.substring(JwtAuthorizationTokenFilter.JWT_TOKEN_HEADER_PREFIX.length + 1)
}

private fun HttpServletRequest.getTokenFromCookie(): String? {
    return cookies?.find { it.name == JwtAuthorizationTokenFilter.JWT_TOKEN_HEADER_NAME }?.value
        ?.let { URLDecoder.decode(it, "UTF-8") }
        ?.takeIf { it.startsWith("${JwtAuthorizationTokenFilter.JWT_TOKEN_HEADER_PREFIX} ") }
        ?.substring(JwtAuthorizationTokenFilter.JWT_TOKEN_HEADER_PREFIX.length + 1)
}
