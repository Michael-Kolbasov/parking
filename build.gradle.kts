plugins {
    kotlin("jvm") version "1.5.31"
    java
}

group = "swedbank.mkolbasov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val kotlinVersion by extra { "1.5.31" }
val junitVersion by extra { "5.8.1" }

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
