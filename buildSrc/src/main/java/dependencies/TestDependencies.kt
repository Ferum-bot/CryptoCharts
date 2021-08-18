package dependencies

object TestDependencies {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.JUNIT_ANDROID}"

    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"

    const val MOCKITO_KOTLIN = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.MOCKITO_KOTLIN}"

    const val POWERMOCK_API = "org.powermock:powermock-api-mockito2:${Versions.POWERMOCK}"
    const val POWERMOCK_MODULE = "org.powermock:powermock-module-junit4:${Versions.POWERMOCK}"

}