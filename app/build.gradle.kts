plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.safeArgs)
    id(Plugins.hilt)
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
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
        jvmTarget = "1.8"
    }

    android.buildFeatures.viewBinding = true
}

dependencies {

    implementation(Dependencies.standardLibs)
    implementation(Dependencies.lifecycleLibs)
    implementation(Dependencies.activityFragmentKtxLibs)
    implementation(Dependencies.uILibs)
    implementation(Dependencies.navigationLibs)
    implementation(Dependencies.retrofitLibs)
    implementation(Dependencies.roomLibs)
    implementation(Dependencies.diLibs)
    implementation(Dependencies.coroutinesLibs)

    testImplementation(TestDependencies.testLibraries)

    androidTestImplementation(AndroidTestDependencies.androidTestLibraries)
}
