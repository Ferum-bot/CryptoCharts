package module_plugins

import BuildTypeDebug
import BuildTypeRelease
import Config
import dependencies.Dependencies
import extensions.implementation
import extensions.addBaseCoreDependencies
import extensions.addAllUIDependencies
import extensions.addAllDIDependencies
import extensions.project

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Config.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(Config.MIN_SDK_VERSION)
    }

    buildTypes {
        getByName(BuildTypeRelease.name) {}
        getByName(BuildTypeDebug.name) {}
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets.forEach {
        it.java.setSrcDirs(it.java.srcDirs + "src/$it.name/kotlin")
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.DAGGER)
    implementation(Dependencies.TIMBER)

    implementation(project(Core.Base))
    implementation(project(Core.Data))
    implementation(project(Core.Network))

    implementation(project(Additional.UIKit))
    implementation(project(Additional.Security))

    addBaseCoreDependencies()

    addAllUIDependencies()

    addAllDIDependencies()
}