plugins {
    kotlin("jvm") version "1.7.10"
}

group = "me.zenotick"
version = "1.0"

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}