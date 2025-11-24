plugins {
    id("android-library-convention")
}

android {
    namespace = "com.dak0ta.ricktionary.feature.home.data"
}

dependencies {
    implementation(projects.feature.home.domain)

    implementation(projects.core.coroutine)
    implementation(projects.core.di)
    implementation(projects.core.network.domain)

    implementation(libs.androidx.paging.common)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.paging.common)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.paging.testing.android)
}
