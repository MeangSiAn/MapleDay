plugins {
    alias(libs.plugins.mapleday.android.library)
    alias(libs.plugins.mapleday.android.hilt)
}

android {
    namespace = "com.mosstis.mapleday.core.security"
}

dependencies {
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.runner)
}
