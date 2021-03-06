import java.lang.System.getProperty
import java.util.*

buildscript {
    repositories {
        mavenLocal()
        maven(url = "https://mirrors.huaweicloud.com/repository/maven")
        jcenter()
        // mavenCentral()
        google()
        // maven (url="https://dl.bintray.com/kotlin/kotlin-eap")
    }

    dependencies {
        val kotlinVersion: String by project
        val atomicFuVersion: String by project

        classpath("com.android.tools.build:gradle:3.5.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        // classpath("com.github.jengelman.gradle.plugins:shadow:5.2.0")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:$atomicFuVersion")
    }
}

plugins {
    id("org.jetbrains.dokka") version "0.10.1" apply false
}

runCatching {
    val keyProps = Properties().apply {
        file("local.properties").takeIf { it.exists() }?.inputStream()?.use { load(it) }
    }
    if (keyProps.getProperty("sdk.dir", "").isNotEmpty()) {
        project.ext.set("isAndroidSDKAvailable", true)
    } else {
        project.ext.set("isAndroidSDKAvailable", false)
    }
}

allprojects {
    group = "net.mamoe"
    version = getProperty("miraiVersion")

    repositories {
        mavenLocal()
        maven(url = "https://mirrors.huaweicloud.com/repository/maven")
        jcenter()
        // mavenCentral()
        google()
        // maven (url="https://dl.bintray.com/kotlin/kotlin-eap")
    }
}