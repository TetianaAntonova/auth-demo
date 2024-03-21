package com.anahoret.authdemo.security.config

import com.anahoret.authdemo.auth.web.AuthController.Companion.AUTH_PATH
import com.anahoret.authdemo.security.jwt.JwtAuthorizationTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtAuthorizationTokenFilter: JwtAuthorizationTokenFilter
) {
    // todo

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .cors(Customizer.withDefaults())
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(GET, "/").permitAll()
                it.requestMatchers(GET, "/index").permitAll()
                it.requestMatchers(GET, "/swagger-ui/**").permitAll()
                it.requestMatchers(GET, "/v3/api-docs").permitAll()
                it.requestMatchers(GET, "/v3/api-docs/swagger-config").permitAll()
                it.requestMatchers(GET, "/error").permitAll()
                it.requestMatchers(POST, "$AUTH_PATH/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
