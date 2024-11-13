plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "1.8.0"

}

group = "org.invendiv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}




dependencies {
    val exposedVersion = "0.56.0"
    val ktorVersion = "3.0.0"
    testImplementation(kotlin("test"))
    // Logging dependency is required for server-side applications; omitting it may cause compilation issues
    implementation("ch.qos.logback:logback-classic:1.4.5")

    // Ktor
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-resources:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("com.auth0:java-jwt:3.18.2")

    // Exposed DB
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-money:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:$exposedVersion")
    implementation("com.h2database:h2:1.4.200")


    implementation("com.vladsch.flexmark:flexmark-all:0.64.0")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}