// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.GRADLE)
        classpath(Libs.KOTLIN_GRADLE_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}