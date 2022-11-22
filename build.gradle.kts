plugins {
    java
    `kotlin-dsl`
    application
    id("nu.studer.jooq") version "7.0"
}

group = "ru.zulvit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("nu.studer.jooq")
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
        testImplementation("org.mockito:mockito-core:4.8.1")
        testImplementation("org.mockito:mockito-junit-jupiter:4.8.1")
        compileOnly("org.projectlombok:lombok:1.18.24")

        implementation("org.jooq:jooq:3.17.5")
        implementation("org.jooq:jooq-codegen:3.17.5")
        implementation("org.jooq:jooq-meta:3.17.5")

        implementation("org.flywaydb:flyway-core:9.8.1")
        implementation("org.postgresql:postgresql:42.5.0")
        implementation("org.jetbrains:annotations:23.0.0")
    }
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}