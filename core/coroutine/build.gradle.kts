plugins {
    id("jvm-convention")
    id("kotlin-kapt")
}

dependencies {
    api(libs.kotlinx.coroutines.core)

    implementation(projects.core.di)

    kapt(libs.dagger.compiler)
}
