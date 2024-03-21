package com.anahoret.authdemo

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.PostgreSQLContainer
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    companion object {
        private val postgreSQLContainer = PostgreSQLContainer("postgres:16")
            .withDatabaseName("auth_demo")
            .withUsername("auth_demo")
            .withPassword("auth_demo")
            .apply { start() }
    }

    @Bean
    fun postgreSQLContainer(): PostgreSQLContainer<*> {
        return postgreSQLContainer
    }

    @Bean
    fun testDataSource(postgreSQLContainer: PostgreSQLContainer<*>): DataSource {
        val config = HikariConfig().apply {
            driverClassName = postgreSQLContainer.driverClassName
            jdbcUrl = postgreSQLContainer.jdbcUrl
            username = postgreSQLContainer.username
            password = postgreSQLContainer.password
        }
        return HikariDataSource(config)
    }
}
