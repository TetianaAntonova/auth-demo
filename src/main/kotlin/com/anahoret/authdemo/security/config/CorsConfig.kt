package com.anahoret.authdemo.security.config

import com.anahoret.authdemo.security.CorsSettings
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableConfigurationProperties(CorsSettings::class)
@Configuration
@EnableWebMvc
class CorsConfig(
    private val corsSettings: CorsSettings
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowCredentials(true)
            .allowedOrigins(*corsSettings.allowedOrigins)
            .allowedHeaders("Content-Type", "Authorization")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
    }
}
