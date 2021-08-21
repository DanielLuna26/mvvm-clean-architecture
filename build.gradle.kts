// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.gradle)
        classpath(BuildPlugins.gradlePlugin)
        classpath(BuildPlugins.safeArgs)
        classpath(BuildPlugins.hiltDagger)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

task<Delete> ("clean") {
    delete(rootProject.buildDir)
}