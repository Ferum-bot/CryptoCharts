package extensions

import org.gradle.api.artifacts.dsl.RepositoryHandler

fun RepositoryHandler.addAllRepositories() {
    google()
    jcenter()
    mavenCentral()
}