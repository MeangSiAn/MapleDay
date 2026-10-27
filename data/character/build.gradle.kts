plugins {
    alias(libs.plugins.mapleday.android.library)
    alias(libs.plugins.mapleday.android.hilt)
    alias(libs.plugins.mapleday.retrofit)
}

android {
    namespace = "com.mosstis.mapleday.data.character"
}

dependencies {
    implementation(project(":domain:character"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))

    testImplementation(libs.junit)
}
