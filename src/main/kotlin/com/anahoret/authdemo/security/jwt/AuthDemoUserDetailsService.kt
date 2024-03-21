package com.anahoret.authdemo.security.jwt

import com.anahoret.authdemo.users.credentials.UserCredentialsService
import com.anahoret.authdemo.users.domain.UserRole
import com.anahoret.authdemo.users.service.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthDemoUserDetailsService(
    private val userService: UserService,
    private val userCredentialsService: UserCredentialsService
) : UserDetailsService {

    override fun loadUserByUsername(email: String?): UserDetails {
        return email
            ?.let { userService.getByEmail(email) }
            ?.let { user -> userCredentialsService.getUserCredentials(email)?.let { it to user } }
            ?.let { (userCredentials, user) ->
                AuthDemoUserDetails(
                    id = user.id,
                    email = userCredentials.email,
                    password = userCredentials.passwordHash,
                    active = true,
                    authorities = user.roles.toGrantedAuthorities()
                )
            }
            ?: throw UsernameNotFoundException("No user found with username '$email'.")
    }

    private fun List<UserRole>.toGrantedAuthorities(): MutableCollection<GrantedAuthority> {
        return map { SimpleGrantedAuthority("ROLE_${it.name}") }.toMutableList()
    }
}
