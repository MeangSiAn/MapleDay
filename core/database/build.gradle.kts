plugins {
    alias(libs.plugins.mapleday.android.library)
    alias(libs.plugins.mapleday.android.hilt)
    alias(libs.plugins.mapleday.android.room)
}

android {
    namespace = "com.mosstis.mapleday.core.database"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}
