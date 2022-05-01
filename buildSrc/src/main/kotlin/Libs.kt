object Libs {

    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:" + Version.KOTLIN

    //Gradle
    const val GRADLE = "com.android.tools.build:gradle:" + Version.GRADLE
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:" + Version.KT_GRADLE_PLUGIN

    const val CORE_KTX = "androidx.core:core-ktx:" + Version.CORE
    const val ACTIVITY = "androidx.activity:activity-ktx:" + Version.ACTIVITY

    // Lifecycle components
    const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:" + Version.LIFECYCLE
    const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:" + Version.LIFECYCLE
    const val LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-ktx:" + Version.LIFECYCLE
    const val FRAGMENT = "androidx.fragment:fragment:" + Version.FRAGMENT
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:" + Version.FRAGMENT

    const val APPCOMPAT = "androidx.appcompat:appcompat:" + Version.APPCOMPAT

    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:" + Version.CONSTRAINT_LAYOUT

    // Testing
    const val JUNIT = "junit:junit:" + Version.JUNIT
    const val ANDROIDX_JUNIT = "androidx.test.ext:junit:" + Version.ANDROIDX_JUNIT
    const val EXPRESSO_CORE = "androidx.test.espresso:espresso-core:" + Version.ESPRESSO
    const val CORE_TEST = "androidx.test:core:" + Version.CORE_TESTING
    const val TEST_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

    // Dependency injection
    const val DAGGER = "com.google.dagger:dagger:" + Version.DAGGER
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:" + Version.DAGGER

    const val GSON = "com.google.code.gson:gson:" + Version.GSON

    // Networking
    const val RETROFIT = "com.squareup.retrofit2:retrofit:" + Version.RETROFIT
    const val RETROFIT_CONVERTER = "com.squareup.retrofit2:converter-gson:" + Version.RETROFIT
    const val OKHTTP = "com.squareup.okhttp3:okhttp:" + Version.OKHTTP
    const val HTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:" + Version.HTTP_LOGGING
    const val CHUCKER = "com.github.chuckerteam.chucker:library:" + Version.CHUCKER
    const val CHUCKER_NO_OP = "com.github.chuckerteam.chucker:library-no-op:" + Version.CHUCKER

    // Room components
    const val ROOM = "androidx.room:room-runtime:" + Version.ROOM
    const val ROOM_KTX = "androidx.room:room-ktx:" + Version.ROOM
    const val ROOM_COMPILER = "androidx.room:room-compiler:" + Version.ROOM
    const val ROOM_TESTING = "androidx.room:room-testing:" + Version.ROOM

    // Kotlin components
    const val COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:" + Version.COROUTINES
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:" + Version.COROUTINES

    // UI
    const val MATERIAL = "com.google.android.material:material:" + Version.MATERIAL

    //Image
    const val GLIDE = "com.github.bumptech.glide:glide:" + Version.GLIDE
}