plugins {
    id("android-library-compose-convention")
}

android {
    namespace = "com.dak0ta.ricktionary.feature.home.presentation"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/LICENSE-notice.txt"
            excludes += "META-INF/NOTICE-notice.md"
            excludes += "META-INF/NOTICE-notice.txt"
        }
    }
}

dependencies {
    implementation(projects.feature.home.domain)

    implementation(projects.core.coroutine)
    implementation(projects.core.design)
    implementation(projects.core.di)
    implementation(projects.core.mvvm)
    implementation(projects.core.navigationCompose)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.paging.testing)
}
