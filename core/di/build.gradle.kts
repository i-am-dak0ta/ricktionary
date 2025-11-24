plugins {
    id("jvm-convention")
    id("kotlin-kapt")
}

dependencies {
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.dagger)
    api(libs.javax.inject)

    kapt(libs.dagger.compiler)
}
