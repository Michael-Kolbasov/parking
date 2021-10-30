import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.jpa") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.liquibase.gradle") version "2.0.4"
}

group = "swedbank.mkolbasov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val postgresqlDriverVersion by extra { "42.3.0" }
val apacheCommonsVersion by extra { "3.12.0" }
val swaggerVersion by extra { "2.9.2" }

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.springfox:springfox-swagger2:$swaggerVersion")
    implementation("io.springfox:springfox-swagger-ui:$swaggerVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.postgresql:postgresql:$postgresqlDriverVersion")
    implementation("org.apache.commons:commons-lang3:$apacheCommonsVersion")

    runtimeOnly("org.liquibase:liquibase-core")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "15"
        freeCompilerArgs = freeCompilerArgs + "-Xemit-jvm-type-annotations"
        freeCompilerArgs = freeCompilerArgs + "-Xjsr305=strict"
    }
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = "15"
    targetCompatibility = "15"
}
