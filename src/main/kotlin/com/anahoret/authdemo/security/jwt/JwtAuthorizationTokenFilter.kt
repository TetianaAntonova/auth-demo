package com.anahoret.authdemo.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthorizationTokenFilter(
    @Lazy private val userDetailsService: UserDetailsService,
    private val jwtTokenUtil: JwtTokenUtil
) : OncePerRequestFilter() {

    companion object {
        const val JWT_TOKEN_HEADER_NAME = "Authorization"
        const val JWT_TOKEN_HEADER_PREFIX = "Bearer"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.getToken()
            ?.let { jwtTokenUtil.verifyAndDecode(it) }
            ?.let {
                val securityContext = SecurityContextHolder.getContext()
                if (securityContext.authentication == null) {
                    logger.debug("Security context was null, so authenticating user.")
                    authenticateUser(securityContext, it.subject, request)
                }
            }

        filterChain.doFilter(request, response)
    }

    private fun authenticateUser(securityContext: SecurityContext, username: String, request: HttpServletRequest) {
        val userDetails = try {
            userDetailsService.loadUserByUsername(username)
                .takeIf { it.isEnabled && it.isAccountNonLocked && it.isAccountNonExpired && it.isCredentialsNonExpired }
                ?.let { it as? AuthDemoUserDetails }

        } catch (e: UsernameNotFoundException) {
            logger.error("Cannot authenticate user. User not found.")
            null
        } ?: return
        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        logger.debug("Authorized user '$username', setting security context.")
        securityContext.authentication = authentication
    }

}
