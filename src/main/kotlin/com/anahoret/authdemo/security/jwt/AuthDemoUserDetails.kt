package com.anahoret.authdemo.security.jwt

import com.anahoret.authdemo.users.domain.UserRole
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AuthDemoUserDetails(
    @get:JsonIgnore val id: Long,
    private val email: String,
    private val password: String,
    private val active: Boolean,
    private val authorities: MutableCollection<GrantedAuthority>
) : UserDetails {

    val userRoles = authorities.map { UserRole.roleNameMap.getValue(it.authority) }
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    @JsonIgnore
    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
