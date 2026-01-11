//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)
//}
//
//android {
//    namespace = "com.example.secondapp"
//    compileSdk {
//        version = release(36)
//    }
//
//    defaultConfig {
//        applicationId = "com.example.secondapp"
//        minSdk = 24
//        targetSdk = 36
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//    buildFeatures {
//        compose = true
//    }
//}
//
//
//    dependencies {
//
//
//        implementation(libs.androidx.core.ktx)
//        implementation(libs.androidx.lifecycle.runtime.ktx)
//        implementation(libs.androidx.activity.compose)
//        implementation(platform(libs.androidx.compose.bom))
//        implementation(libs.androidx.compose.ui)
//        implementation(libs.androidx.compose.ui.graphics)
//        implementation(libs.androidx.compose.ui.tooling.preview)
//        implementation(libs.androidx.compose.material3)
//        implementation(libs.androidx.room.ktx)
//        implementation(libs.compose.material3)
//        implementation(libs.androidx.material3)
//        testImplementation(libs.junit)
//        androidTestImplementation(libs.androidx.junit)
//        androidTestImplementation(libs.androidx.espresso.core)
//        androidTestImplementation(platform(libs.androidx.compose.bom))
//        androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//        debugImplementation(libs.androidx.compose.ui.tooling)
//        debugImplementation(libs.androidx.compose.ui.test.manifest)
//    }
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.secondapp"
    // In 2025, use the direct assignment or the release function for API 36
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.secondapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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

    // Modern Android development (AGP 8.x+) strongly recommends Java 17
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core & Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM & UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.constraintlayout.compose)
    implementation(libs.accompanist.permissions)
    // Material 3 (Only one reference is needed; removed duplicates)
    implementation(libs.androidx.compose.material3)

    // Database
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.media3.common.ktx)


    // ...
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.ui)

    // ...

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
