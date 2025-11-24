plugins {
    id("jvm-convention")
}

dependencies {
    api(projects.core.domain)
    api(projects.core.navigation)

    implementation(projects.core.coroutine)

    implementation(libs.androidx.paging.common)
}
