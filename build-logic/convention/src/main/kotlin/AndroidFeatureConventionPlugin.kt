import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import com.mosstis.mapleday.buildlogic.library
import com.mosstis.mapleday.buildlogic.libs

/**
 * :feature:* 모듈 공통 설정 = library + compose + hilt + type-safe navigation + core UI/presentation 의존.
 * domain 의존은 feature마다 다르므로 각 모듈에서 선언한다.
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("mapleday.android.library")
        pluginManager.apply("mapleday.android.compose")
        pluginManager.apply("mapleday.android.hilt")
        // @Serializable 라우트(type-safe navigation)용
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        dependencies {
            add("implementation", project(":core:ui"))
            add("implementation", project(":core:presentation"))
            add("implementation", project(":core:designsystem"))

            add("implementation", libs.library("androidx-compose-material3"))
            add("implementation", libs.library("androidx-lifecycle-runtime-compose"))
            add("implementation", libs.library("androidx-lifecycle-viewmodel-compose"))
            add("implementation", libs.library("androidx-hilt-lifecycle-viewmodel-compose"))
            add("implementation", libs.library("androidx-navigation-compose"))
        }
    }
}
