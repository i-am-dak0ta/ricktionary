plugins {
    id("android-library-compose-convention")
}

android {
    namespace = "com.dak0ta.ricktionary.core.navigation.compose"
}

dependencies {
    api(projects.core.navigation)

    api(libs.androidx.navigation.compose)
}
