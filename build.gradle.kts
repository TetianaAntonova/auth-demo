import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val auth0Version = "4.4.0"
val flywaydbVersion = "10.10.0"
val hibernateValidatorVersion = "8.0.1.Final"

plugins {
    val jvmVersion = "1.9.22"
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version jvmVersion
    kotlin("plugin.spring") version jvmVersion
    kotlin("plugin.jpa") version jvmVersion
}

group = "com.anahoret"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Security
    implementation("com.auth0:java-jwt:$auth0Version")

    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Data
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core:$flywaydbVersion")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.hibernate.validator:hibernate-validator:$hibernateValidatorVersion")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:$flywaydbVersion")

    // Tests
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<Jar>("jar") {
    enabled = false
}
