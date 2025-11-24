rootProject.name = "ricktionary"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

listOf("app", "core", "feature")
    .map { file(it) }
    .filter { it.exists() && it.isDirectory }
    .forEach { includeModulesRecursively(it, ":${it.name}") }

fun includeModulesRecursively(directory: File, parentPath: String = "") {
    val hasBuildFile = directory.resolve("build.gradle.kts").exists() || directory.resolve("build.gradle").exists()

    if (hasBuildFile) {
        include(parentPath)
        project(parentPath).projectDir = directory
        logger.lifecycle("Included $parentPath from ${directory.path}")
    }

    directory.listFiles()
        ?.filter { it.isDirectory && !it.name.startsWith(".") && it.name !in setOf("build", "gradle") }
        ?.forEach { subDir ->
            val modulePath = "$parentPath:${subDir.name}"
            includeModulesRecursively(subDir, modulePath)
        }
}
