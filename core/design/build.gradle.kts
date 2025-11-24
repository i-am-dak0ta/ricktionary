plugins {
    id("android-library-compose-convention")
}

android {
    namespace = "com.dak0ta.ricktionary.core.design"
}

dependencies {
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
}
