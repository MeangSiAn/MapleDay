import java.util.Properties

plugins {
    alias(libs.plugins.mapleday.android.library)
    alias(libs.plugins.mapleday.android.hilt)
    alias(libs.plugins.mapleday.retrofit)
}

// 앱 토큰 같은 비밀값은 git에서 제외된 local.properties에서만 읽는다 (SPEC 5.1)
val localProperties = Properties().apply {
    rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use(::load)
}

android {
    namespace = "com.mosstis.mapleday.core.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            "String",
            "NAS_BASE_URL",
            "\"${localProperties.getProperty("NAS_BASE_URL", "https://mp.mosstis.com/")}\"",
        )
        buildConfigField("String", "APP_TOKEN", "\"${localProperties.getProperty("APP_TOKEN", "")}\"")
    }
}

dependencies {
    api(project(":core:common"))
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
