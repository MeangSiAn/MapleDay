plugins {
    alias(libs.plugins.mapleday.android.feature)
}

android {
    namespace = "com.mosstis.mapleday.feature.main"
}

dependencies {
    implementation(project(":domain:user"))
    implementation(project(":domain:character"))
}
