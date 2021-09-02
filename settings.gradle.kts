pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "colop-kmm"
include(":androidApp")
include(":shared")
enableFeaturePreview("VERSION_CATALOGS")
