package com.mosstis.mapleday.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

val JAVA_VERSION = JavaVersion.VERSION_17

/**
 * application/library 공통 Android 설정.
 * AGP 9 Built-in Kotlin은 jvmTarget을 compileOptions.targetCompatibility에서 따라가므로 별도 설정하지 않는다.
 */
internal fun Project.configureAndroidApplication(extension: ApplicationExtension) {
    extension.apply {
        compileSdk = libs.version("compileSdk").toInt()
        defaultConfig {
            minSdk = libs.version("minSdk").toInt()
            targetSdk = libs.version("targetSdk").toInt()
        }
        compileOptions {
            sourceCompatibility = JAVA_VERSION
            targetCompatibility = JAVA_VERSION
            // minSdk 24에서 java.time 사용 (API 26+)
            isCoreLibraryDesugaringEnabled = true
        }
    }
    dependencies {
        add("coreLibraryDesugaring", libs.library("desugar-jdk-libs"))
    }
}

internal fun Project.configureAndroidLibrary(extension: LibraryExtension) {
    extension.apply {
        compileSdk = libs.version("compileSdk").toInt()
        defaultConfig {
            minSdk = libs.version("minSdk").toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        compileOptions {
            sourceCompatibility = JAVA_VERSION
            targetCompatibility = JAVA_VERSION
            // minSdk 24에서 java.time 사용 (API 26+)
            isCoreLibraryDesugaringEnabled = true
        }
    }
    dependencies {
        add("coreLibraryDesugaring", libs.library("desugar-jdk-libs"))
    }
}

internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JAVA_VERSION
        targetCompatibility = JAVA_VERSION
    }
    extensions.configure<KotlinJvmProjectExtension> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}
