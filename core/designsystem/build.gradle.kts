plugins {
    alias(libs.plugins.mapleday.android.library)
    alias(libs.plugins.mapleday.android.compose)
}

android {
    namespace = "com.mosstis.mapleday.core.designsystem"
}

dependencies {
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
}
