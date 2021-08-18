package flavor

import com.android.build.gradle.internal.dsl.ProductFlavor
import org.gradle.api.Action
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.extra

sealed class EnvironmentFlavor : ProductFlavors {
    open val appIdSuffix: String? = null
    open val wsVersion: Int = 4

    override fun configureActionForApp(): Action<ProductFlavor> {
        return Action {
            if (appIdSuffix != null) {
                this.applicationIdSuffix = ".$appIdSuffix"
                this.versionNameSuffix = "-$appIdSuffix"
            }
            this.buildConfigField("int", "WS_VERSION", "$wsVersion")
            if (flavorName == Dev.flavorName) {
                this.resConfigs("en", "xxhdpi")
                (this as ExtensionAware).extra["alwaysUpdateBuildId"] = false
            }
        }
    }

    override fun configureActionForLib(): Action<ProductFlavor> {
        return Action {
            if (appIdSuffix != null) {
                this.versionNameSuffix = "-$appIdSuffix"
            }
            this.buildConfigField("int", "WS_VERSION", "$wsVersion")
            if (flavorName == Dev.flavorName) {
                this.resConfigs("en", "xxhdpi")
                (this as ExtensionAware).extra["alwaysUpdateBuildId"] = false
            }
        }
    }

    object Master : EnvironmentFlavor() {
        override val flavorName = "master"
    }

    object Dev : EnvironmentFlavor() {
        override val flavorName = "dev"
        override val appIdSuffix = "debug"
    }

    object Prerelease : EnvironmentFlavor() {
        override val flavorName = "prerelease"
        override val appIdSuffix = "prerelease"
    }
}