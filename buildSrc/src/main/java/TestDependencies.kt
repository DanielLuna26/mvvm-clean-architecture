import org.gradle.api.artifacts.dsl.DependencyHandler

object TestDependencies {
    const val junit = "junit:junit:${Versions.junit}"

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}