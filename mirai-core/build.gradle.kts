@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform")
    id("kotlinx-atomicfu")
    id("kotlinx-serialization")
    id("org.jetbrains.dokka")
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4-jetbrains-3"
}

val kotlinVersion: String by rootProject.ext
val atomicFuVersion: String by rootProject.ext
val coroutinesVersion: String by rootProject.ext
val kotlinXIoVersion: String by rootProject.ext
val coroutinesIoVersion: String by rootProject.ext


val ktorVersion: String by rootProject.ext

val serializationVersion: String by rootProject.ext

fun kotlinx(id: String, version: String) = "org.jetbrains.kotlinx:kotlinx-$id:$version"

fun ktor(id: String, version: String) = "io.ktor:ktor-$id:$version"


description = "QQ protocol library"

val isAndroidSDKAvailable: Boolean by project

val miraiVersion: String by project
version = miraiVersion

kotlin {
    if (isAndroidSDKAvailable) {
        apply(from = rootProject.file("gradle/android.gradle"))
        android("android") {
            publishAllLibraryVariants()
        }
    } else {
        println(
            """Android SDK 可能未安装.
                $name 的 Android 目标编译将不会进行. 
                这不会影响 Android 以外的平台的编译.
            """.trimIndent()
        )
        println(
            """Android SDK might not be installed.
                Android target of $name will not be compiled. 
                It does no influence on the compilation of other platforms.
            """.trimIndent()
        )
    }

    jvm()

    sourceSets {
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")
        }

        commonMain {
            dependencies {
                api(kotlin("stdlib", kotlinVersion))
                api(kotlin("serialization", kotlinVersion))
                api(kotlin("reflect", kotlinVersion))

                api(kotlinx("coroutines-core-common", coroutinesVersion))
                api(kotlinx("serialization-runtime-common", serializationVersion))
                api(kotlinx("serialization-protobuf-common", serializationVersion))
                api(kotlinx("io", kotlinXIoVersion))
                api(kotlinx("coroutines-io", coroutinesIoVersion))
                api(kotlinx("coroutines-core", coroutinesVersion))

                api("org.jetbrains.kotlinx:atomicfu-common:$atomicFuVersion")

                api(ktor("client-cio", ktorVersion))
                api(ktor("client-core", ktorVersion))
                api(ktor("network", ktorVersion))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-common"))
            }
        }

        if (isAndroidSDKAvailable) {
            val androidMain by getting {
                dependencies {
                    api(kotlin("reflect", kotlinVersion))

                    api(kotlinx("io-jvm", kotlinXIoVersion))
                    api(kotlinx("serialization-runtime", serializationVersion))
                    api(kotlinx("serialization-protobuf", serializationVersion))
                    api(kotlinx("coroutines-android", coroutinesVersion))
                    api(kotlinx("coroutines-io-jvm", coroutinesIoVersion))

                    api(ktor("client-android", ktorVersion))
                }
            }

            val androidTest by getting {
                dependencies {
                    implementation(kotlin("test", kotlinVersion))
                    implementation(kotlin("test-junit", kotlinVersion))
                    implementation(kotlin("test-annotations-common"))
                    implementation(kotlin("test-common"))
                }
            }
        }

        val jvmMain by getting {
            dependencies {
                //api(kotlin("stdlib-jdk8", kotlinVersion))
                //api(kotlin("stdlib-jdk7", kotlinVersion))
                api(kotlin("reflect", kotlinVersion))

                api(ktor("client-core-jvm", ktorVersion))
                api(kotlinx("io-jvm", kotlinXIoVersion))
                api(kotlinx("serialization-runtime", serializationVersion))
                api(kotlinx("serialization-protobuf", serializationVersion))
                api(kotlinx("coroutines-io-jvm", coroutinesIoVersion))
                api(kotlinx("coroutines-core", coroutinesVersion))

                api("org.bouncycastle:bcprov-jdk15on:1.64")
                runtimeOnly(files("build/classes/kotlin/jvm/main")) // classpath is not properly set by IDE
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test", kotlinVersion))
                implementation(kotlin("test-junit", kotlinVersion))
                implementation("org.pcap4j:pcap4j-distribution:1.8.2")

                runtimeOnly(files("build/classes/kotlin/jvm/test")) // classpath is not properly set by IDE
            }
        }
    }
}
//
//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//    kotlinOptions.jvmTarget = "1.8"
//}
tasks {
    val dokka by getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
    }
    val dokkaMarkdown by creating(org.jetbrains.dokka.gradle.DokkaTask::class) {
        outputFormat = "markdown"
        outputDirectory = "$buildDir/dokka-markdown"
    }
}

apply(from = rootProject.file("gradle/publish.gradle"))
