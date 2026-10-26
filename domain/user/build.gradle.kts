// domain은 Android 의존 없는 순수 Kotlin 모듈
plugins {
    alias(libs.plugins.mapleday.jvm.library)
}

dependencies {
    // NexonApiError를 UseCase 계약(전달되는 오류)으로 노출하므로 api
    api(project(":core:common"))

    // Repository 인터페이스가 Flow를 public API로 노출하므로 api
    api(libs.kotlinx.coroutines.core)
    // UseCase @Inject constructor용 (Hilt 없이 표준 어노테이션만)
    implementation(libs.javax.inject)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
