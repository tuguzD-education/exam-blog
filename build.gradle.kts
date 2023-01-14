import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by project
val ktor_version: String by project
val koin_version: String by project
val kmongo_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.8.0"
    id("io.ktor.plugin") version "2.2.2"
    kotlin("plugin.serialization") version "1.8.0"
}

group = "io.github.tuguzd"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Core application
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-locations-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")

    // Dependency injection
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    // Serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    // MongoDB Driver
    implementation("org.litote.kmongo:kmongo:$kmongo_version")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-client-encoding:2.1.3")

    // Testing
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation(kotlin("stdlib-jdk8"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
