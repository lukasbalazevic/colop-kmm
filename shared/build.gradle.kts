import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

version = "1.0"
sqldelight {
    database("CommonDatabase") {
        packageName = "com.futured.app.db"
    }
}

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        frameworkName = "shared"
        podfile = project.file("../iosApp/Podfile")
    }

    sourceSets {
        val libs = project.extensions.getByType<VersionCatalogsExtension>()
            .named("libs") as org.gradle.accessors.dm.LibrariesForLibs

        val commonMain by getting {
            dependencies {
                // Coroutines
                implementation(libs.kotlinx.coroutinesMt)
                // Serialization
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.serializationJson)
                // Ktor
                implementation(libs.ktor.client.core)
                // SqlDelight
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.ext)
                // Arkitekt
                api("app.futured.arkitekt:km-usecases:0.1.1-SNAPSHOT")
                implementation("io.ktor:ktor-network:1.6.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                // Ktor android engine
                implementation(libs.ktor.client.android)
                // SqlDelight android driver
                implementation(libs.sqldelight.android)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                // Ktor ios engine
                implementation(libs.ktor.client.ios)
                // SqlDelight iOS driver
                implementation(libs.sqldelight.ios)
                // Arkitekt viewmodel
                api("app.futured.arkitekt:km-viewmodel:0.1.2-SNAPSHOT")
            }
        }
        val iosTest by getting
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

android {
    compileSdk = 30
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
        targetSdk = 30
    }
}
