buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.androidPlugin}")
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.4.2.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
