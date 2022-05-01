plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = DefaultConfig.compileSdk

    defaultConfig {
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk

        testInstrumentationRunner = Libs.TEST_RUNNER
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = DefaultConfig.jvmTarget
    }
}

dependencies {

    implementation(Libs.KOTLIN)

    implementation(Libs.CORE_KTX)
    testImplementation(Libs.JUNIT)
    androidTestImplementation(Libs.ANDROIDX_JUNIT)

    // Networking
    api(Libs.RETROFIT)
    api(Libs.RETROFIT_CONVERTER)
    api(Libs.HTTP_LOGGING)
    debugImplementation(Libs.CHUCKER)
    releaseImplementation(Libs.CHUCKER_NO_OP)

    // Room components
    api(Libs.ROOM)
    api(Libs.ROOM_KTX)
    kapt(Libs.ROOM_COMPILER)
    androidTestImplementation(Libs.ROOM_TESTING)

    implementation(Libs.GSON)

    // Coroutines
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES)

    // DI
    implementation(Libs.DAGGER)
    kapt(Libs.DAGGER_COMPILER)

    //Why??
    debugImplementation(Libs.CHUCKER)
    releaseImplementation(Libs.CHUCKER_NO_OP)
}