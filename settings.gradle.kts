pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MapleDay"
include(":app")

include(":core:common")
include(":core:presentation")
include(":core:designsystem")
include(":core:ui")
include(":core:database")
include(":core:security")
include(":core:network")

include(":feature:main")
include(":feature:splash")
include(":feature:connect")

include(":domain:user")
include(":domain:character")
include(":data:user")
include(":data:character")
