import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    private const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    private const val material = "com.google.android.material:material:${Versions.material}"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val support = "androidx.legacy:legacy-support-v4:${Versions.support}"

    val standardLibs = arrayListOf<String>().apply {
        add(stdLib)
        add(coreKtx)
        add(appCompat)
        add(material)
        add(constraintLayout)
        add(support)
    }

    private const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifeCycle}"
    private const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}"
    private const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycle}"
    private const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifeCycle}"
    private const val savedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifeCycle}"

    val lifecycleLibs = arrayListOf<String>().apply {
        add(lifecycleRuntime)
        add(viewModel)
        add(liveData)
        add(lifecycleCompiler)
        add(savedState)
    }

    // Fragment and activity
    private const val fragmentRuntime = "androidx.fragment:fragment:${Versions.fragment}"
    private const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    private const val activityKtx = "androidx.activity:activity-ktx:${Versions.activity}"

    val activityFragmentKtxLibs = arrayListOf<String>().apply {
        add(fragmentRuntime)
        add(fragmentKtx)
        add(activityKtx)
    }

    // UI Dependencies
    private const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    private const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    private const val glideRuntime = "com.github.bumptech.glide:glide:${Versions.glide}"
    private const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    val uILibs = arrayListOf<String>().apply {
        add(cardView)
        add(recyclerView)
        add(glideCompiler)
        add(glideRuntime)
    }

    // Navigation
    private const val navigationRuntime = "androidx.navigation:navigation-runtime:${Versions.navigation}"
    private const val navigationRuntimeKtx = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
    private const val navigationUIKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    private const val navigationUI = "androidx.navigation:navigation-ui:${Versions.navigation}"
    private const val navigationFragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    private const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"

    val navigationLibs = arrayListOf<String>().apply {
        add(navigationRuntime)
        add(navigationRuntimeKtx)
        add(navigationUIKtx)
        add(navigationUI)
        add(navigationFragment)
        add(navigationFragmentKtx)
    }

    // Retrofit
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private const val okHttpLoging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLogging}"
    private const val gson = "com.google.code.gson:gson:${Versions.gson}"

    val retrofitLibs = arrayListOf<String>().apply {
        add(retrofit)
        add(retrofitGson)
        add(okHttpLoging)
        add(gson)
    }

    // Persistence
    private const val dataStore = "androidx.datastore:datastore-preferences:${Versions.dataStore}"
    private const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    val roomLibs = arrayListOf<String>().apply {
        add(dataStore)
        add(roomRuntime)
        add(roomKtx)
        add(roomCompiler)
    }

    // Dependency injection
    private const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    private const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    val diLibs = arrayListOf<String>().apply {
        add(hiltAndroid)
        add(hiltCompiler)
    }

    // Coroutines
    private const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    private const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    val coroutinesLibs = arrayListOf<String>().apply {
        add(coroutinesCore)
        add(coroutines)
    }
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}