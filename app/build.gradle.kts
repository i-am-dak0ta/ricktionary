import com.android.build.api.variant.impl.VariantOutputImpl

plugins {
    id("android-application-convention")
    id("kotlin-kapt")
    alias(libs.plugins.ksp)
}

val vrsCode: String by project
val vrsName: String by project
val appNameFile: String by project

android {
    namespace = "com.dak0ta.ricktionary.app"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

androidComponents {
    onVariants { variant ->
        variant.outputs.filterIsInstance<VariantOutputImpl>().forEach { output ->
            output.outputFileName.set(
                "$appNameFile-${variant.buildType}-$vrsName-$vrsCode.apk",
            )
        }
    }
}

dependencies {
    implementation(projects.feature.home.data)
    implementation(projects.feature.home.domain)
    implementation(projects.feature.home.presentation)

    implementation(projects.core.coroutine)
    implementation(projects.core.di)
    implementation(projects.core.domain)
    implementation(projects.core.mvvm)
    implementation(projects.core.navigation)
    implementation(projects.core.navigationCompose)
    implementation(projects.core.network.data)
    implementation(projects.core.network.domain)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockwebserver)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    kapt(libs.dagger.compiler)
    ksp(libs.moshi.kotlin.codegen)
}
