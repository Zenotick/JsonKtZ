plugins {
    kotlin("jvm") version "1.7.10"
}

group = "me.zenotick"
version = "0.1-a"

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