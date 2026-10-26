plugins {
    alias(libs.plugins.mapleday.android.library)
    alias(libs.plugins.mapleday.android.compose)
}

android {
    namespace = "com.mosstis.mapleday.core.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.compose.material3)
}
