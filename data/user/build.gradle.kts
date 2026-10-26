plugins {
    alias(libs.plugins.mapleday.android.library)
    alias(libs.plugins.mapleday.android.hilt)
    alias(libs.plugins.mapleday.retrofit)
}

android {
    namespace = "com.mosstis.mapleday.data.user"
}

dependencies {
    implementation(project(":domain:user"))
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:security"))
    implementation(project(":core:network"))
}
