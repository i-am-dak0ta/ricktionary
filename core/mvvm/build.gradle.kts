plugins {
    id("android-library-compose-convention")
}

android {
    namespace = "com.dak0ta.ricktionary.core.mvvm"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel)
}
