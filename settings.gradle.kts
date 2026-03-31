pluginManagement {
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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FirstAiAndroid"
include(":app")
include(":common:core")
include(":common:ui")
include(":common:network")
include(":common:model")
include(":common:router")
include(":feature:login")
include(":feature:home")
include(":feature:mine")
include(":feature:login_debug")
include(":feature:home_debug")
