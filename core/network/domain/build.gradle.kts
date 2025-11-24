plugins {
    id("jvm-convention")
}

dependencies {
    api(projects.core.domain)
    implementation(libs.androidx.paging.common)
}
