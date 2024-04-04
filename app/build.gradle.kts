import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.wpms"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wpms"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("com.android.support:support-annotations:28.0.0")
    implementation("androidx.annotation:annotation:1.7.1")
    kapt("androidx.room:room-compiler:2.6.1")
    kapt("android.arch.persistence.room:compiler:1.1.1")
//    implementation "androidx.room:room-runtime:your_room_version"
//    kapt "androidx.room:room-compiler:your_room_version"

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
}