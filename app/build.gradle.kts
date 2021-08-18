import dependencies.Dependencies
import extensions.*
import org.gradle.kotlin.dsl.implementation

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.KOTLIN_ALLOPEN)
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = Config.APPLICATION_ID
        minSdkVersion(Config.MIN_SDK_VERSION)
        targetSdkVersion(Config.TARGET_SDK_VERSION)
        versionCode = 1
        versionName = "1.0.0"
        multiDexEnabled = Config.MULTIDEX_ENABLED

        testInstrumentationRunner = Config.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        BuildTypeRelease.createOrConfig(this)
        BuildTypeDebug.createOrConfig(this)
    }

    flavorDimensions(flavor.FlavorDimensions.ENVIRONMENT)

    buildFeatures {
        viewBinding = true
    }

    productFlavors {
        flavor.EnvironmentFlavor.Master.createOrConfigForApp(this)
        flavor.EnvironmentFlavor.Dev.createOrConfigForApp(this)
        flavor.EnvironmentFlavor.Prerelease.createOrConfigForApp(this)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions.apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    sourceSets.forEach {
        it.java.setSrcDirs(it.java.srcDirs + "src/$it.name/kotlin")
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.DAGGER)
    implementation(Dependencies.TIMBER)

    // Dependency Injection
    addAllDIDependencies()

    // UI
    addAllUIDependencies()

    // Base core
    addBaseCoreDependencies()

    // Network
    addAllNetworkDependencies()

    // Android Lifecycle
    addLifecycleDependencies()

    // Glide
    addGlideDependencies()

    // Room data base
    addRoomDependencies()

    // Work Manager
    addWorkManagerDependencies()

    // Test
    addAllTestDependencies()
}