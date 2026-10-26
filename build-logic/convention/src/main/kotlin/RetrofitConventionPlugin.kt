import com.mosstis.mapleday.buildlogic.library
import com.mosstis.mapleday.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * 네트워크 통신 모듈(:data:*)용. Retrofit + OkHttp + kotlinx.serialization.
 */
class RetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        dependencies {
            add("implementation", libs.library("retrofit"))
            add("implementation", libs.library("retrofit-converter-kotlinx-serialization"))
            add("implementation", platform(libs.library("okhttp-bom")))
            add("implementation", libs.library("okhttp"))
            add("implementation", libs.library("okhttp-logging-interceptor"))
            add("implementation", libs.library("kotlinx-serialization-json"))
        }
    }
}
