import com.android.build.gradle.internal.dsl.BuildType
import com.google.crypto.tink.signature.SignatureConfig
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

interface BuildTypeCreator {

    val name: String

    fun createOrConfig(container: NamedDomainObjectContainer<BuildType>): BuildType {
        val buildType = container.findByName(name)
        return if (buildType != null) {
            container.getByName(name, configureAction())
        } else {
            container.create(name, configureAction())
        }
    }

    fun configureAction(): Action<in BuildType>
}

object BuildTypeRelease : BuildTypeCreator {

    override val name: String = "release"

    override fun configureAction(): Action<in BuildType> {
        return Action {
            this.isShrinkResources = true
            this.isMinifyEnabled = true
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }
    }
}

object BuildTypeDebug : BuildTypeCreator {

    override val name: String = "debug"

    override fun configureAction(): Action<in BuildType> {
        return Action {}
    }
}