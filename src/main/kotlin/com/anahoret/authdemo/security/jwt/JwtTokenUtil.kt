package com.anahoret.authdemo.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class JwtTokenUtil(
    @Value("\${jwt.token-secret}") val secret: String,
    @Value("\${jwt.user-token-expired-in-days}") val validityInDays: String
) {

    private val algorithm = Algorithm.HMAC256(secret)
    private val jwtVerifier = JWT.require(algorithm).build()

    fun generateJwtToken(jwtUser: AuthDemoUserDetails): String {
        val expiresAt = Date.from(ZonedDateTime.now().plusDays(validityInDays.toLong()).toInstant())
        val token = JWT.create()
            .withSubject(jwtUser.username)
            .withExpiresAt(expiresAt)
            .withClaim("roles", jwtUser.userRoles.map { it.name })
            .sign(algorithm)
        return token
    }

    fun verifyAndDecode(token: String): DecodedJWT? {
        return try {
            jwtVerifier.verify(token)
        } catch (t: Throwable) {
            null
        }
    }

    fun getUserNameFromJwtToken(): String {
        // todo
        return "test@gmail.com"
    }

    fun validateJwtToken(token: String): Boolean {
        // todo
        return true
    }
}
