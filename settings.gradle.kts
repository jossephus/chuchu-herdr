pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // chuchu's plugin API: https://jitpack.io/#jossephus/chuchu
        maven("https://jitpack.io")
    }
}

rootProject.name = "chuchu-herdr"

// Develop against a local chuchu checkout instead of the published plugin API:
//   ./gradlew build -PchuchuDir=../chuchu
// Useful while changing the plugin API and this plugin together.
providers.gradleProperty("chuchuDir").orNull?.let { chuchuDir ->
    includeBuild("$chuchuDir/android") {
        dependencySubstitution {
            substitute(module("com.github.jossephus:chuchu")).using(project(":plugin-api"))
        }
    }
}
