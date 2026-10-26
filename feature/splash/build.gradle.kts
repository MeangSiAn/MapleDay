plugins {
    alias(libs.plugins.mapleday.android.feature)
}

android {
    namespace = "com.mosstis.mapleday.feature.splash"
}

dependencies {
    implementation(project(":domain:user"))
}
