package flavor

import com.android.build.gradle.internal.dsl.ProductFlavor
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

object FlavorDimensions {
    const val ENVIRONMENT = "environment"
}

interface ProductFlavors {

    val flavorName: String

    fun createOrConfigForApp(container: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor {
        val buildType = container.findByName(flavorName)
        return if (buildType != null) {
            container.getByName(flavorName, configureActionForApp())
        } else {
            container.create(flavorName, configureActionForApp())
        }
    }

    fun createOrConfigForLib(container: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor {
        val buildType = container.findByName(flavorName)
        return if (buildType != null) {
            container.getByName(flavorName, configureActionForLib())
        } else {
            container.create(flavorName, configureActionForLib())
        }
    }

    fun configureActionForApp(): Action<ProductFlavor>

    fun configureActionForLib(): Action<ProductFlavor>
}