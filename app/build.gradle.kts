plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk  = DefaultConfig.compileSdk
    buildToolsVersion = DefaultConfig.buildToolsVersion

    defaultConfig {
        applicationId = DefaultConfig.applicationId
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
        versionCode = DefaultConfig.versionCode
        versionName = DefaultConfig.versionName

        testInstrumentationRunner = Libs.TEST_RUNNER
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = DefaultConfig.jvmTarget
    }
    dataBinding {
        isEnabled = true
    }
}

configurations.all {
    resolutionStrategy.force(Libs.OKHTTP)
}

dependencies {

    implementation(project(":shared"))

    implementation(Libs.KOTLIN)
    implementation(Libs.CORE_KTX)
    implementation(Libs.APPCOMPAT)

    testImplementation(Libs.JUNIT)
    androidTestImplementation(Libs.ANDROIDX_JUNIT)
    androidTestImplementation(Libs.EXPRESSO_CORE)
    androidTestImplementation(Libs.CORE_TEST)

    implementation(Libs.DAGGER)
    kapt(Libs.DAGGER_COMPILER)

    implementation(Libs.ACTIVITY)
    implementation(Libs.FRAGMENT)
    implementation(Libs.FRAGMENT_KTX)

    // Lifecycle components
    implementation(Libs.VIEWMODEL)
    implementation(Libs.LIVEDATA)
    implementation(Libs.LIFECYCLE)

    // Kotlin components
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES)

    // UI
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.MATERIAL)

    //Image
    implementation(Libs.GLIDE)

    implementation(Libs.GSON)
}