plugins {
    alias(libs.plugins.mapleday.android.feature)
}

android {
    namespace = "com.mosstis.mapleday.feature.connect"
}

dependencies {
    implementation(project(":domain:user"))
}
