plugins {
    alias(libs.plugins.mapleday.android.application)
    alias(libs.plugins.mapleday.android.compose)
    alias(libs.plugins.mapleday.android.hilt)
}

android {
    namespace = "com.mosstis.mapleday"

    defaultConfig {
        applicationId = "com.mosstis.mapleday"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
}

dependencies {
    implementation(project(":feature:main"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:connect"))
    implementation(project(":data:user"))
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
