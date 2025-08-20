plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    //id("com.google.gms.google-services")

}

android {
    namespace = "com.mertyigit0.vocabcards"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mertyigit0.vocabcards"
        minSdk = 24
        targetSdk = 35
        versionCode = 17
        versionName = "2.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)

    // Navigation (Fragments için)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Retrofit + Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Lottie Animations
    implementation(libs.lottie)

    // Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.rxjava2)

    // ViewPager2
    implementation(libs.androidx.viewpager2)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // WorkManager
    implementation(libs.androidx.work.runtime)       // Java
    implementation(libs.androidx.work.runtime.ktx)  // Kotlin + coroutines

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // Jetpack Compose BOM (Versiyonları senkronize ediyor)
    implementation(platform("androidx.compose:compose-bom:2025.01.01"))

    // Compose
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.compose.ui:ui")
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(platform("androidx.compose:compose-bom:2025.08.00"))
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Compose Material 3 (Yeni tasarım sistemi)
    implementation("androidx.compose.material3:material3")

    // Compose ViewModel + Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.2")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.9.3")

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.01.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    implementation("com.google.android.material:material:1.12.0")
    implementation("com.google.accompanist:accompanist-pager:0.30.1")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.30.1")
}
