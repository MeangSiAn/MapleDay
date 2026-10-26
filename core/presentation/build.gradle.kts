plugins {
    alias(libs.plugins.mapleday.android.library)
}

android {
    namespace = "com.mosstis.mapleday.core.presentation"
}

dependencies {
    implementation(project(":core:common"))

    // BaseViewModel의 public API(ViewModel 상속, StateFlow/Flow 노출)에 포함되므로 api로 노출
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
}
