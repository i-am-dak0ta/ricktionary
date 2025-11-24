import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("base-convention")
}

val javaVersion: String by project

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion.toInt()))
    }
}

kotlin {
    jvmToolchain(javaVersion.toInt())
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}
