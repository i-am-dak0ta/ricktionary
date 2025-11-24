plugins {
    id("android-library-convention")
    id("kotlin-kapt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.dak0ta.ricktionary.core.network.data"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(projects.core.network.domain)

    api(libs.logging.interceptor)
    api(libs.moshi)
    api(libs.retrofit)

    implementation(projects.core.coroutine)
    implementation(projects.core.di)

    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.androidx.paging.runtime)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.truth)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.junit)

    kapt(libs.dagger.compiler)
    ksp(libs.moshi.kotlin.codegen)
}
