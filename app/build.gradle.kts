plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.fitlife"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fitlife"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
            excludes += "META-INF/*.kotlin_module"
        }
    }

}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.compose.material3:material3:1.2.0-alpha05")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha10")
    implementation("androidx.compose.foundation:foundation:1.5.0")
    implementation("androidx.compose.runtime:runtime:1.5.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.compose.material:material-icons-extended:1.5.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-storage-ktx")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.navigation.testing)
    implementation(libs.androidx.benchmark.common)
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    kapt ("com.google.dagger:hilt-compiler:2.51.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")


    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-paging:2.5.1")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-rc01")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.1")

    // Utilities
    implementation("com.jakewharton.timber:timber:4.7.1")
    implementation("com.airbnb.android:lottie:5.0.3")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("io.coil-kt:coil-gif:2.4.0")

    // Unit test
    testImplementation ("junit:junit:4.13.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation("com.squareup.moshi:moshi:1.12.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // MockK para pruebas unitarias e instrumentadas
    testImplementation("io.mockk:mockk:1.13.5")
    androidTestImplementation("io.mockk:mockk-android:1.13.5")
    androidTestImplementation("androidx.navigation:navigation-testing:2.8.4")
    // Dependencia para las pruebas de ViewModel y LiveData
    testImplementation ("androidx.arch.core:core-testing:2.2.0")

    // Dependencia para las pruebas de coroutines (si estás usando coroutines en tu ViewModel)
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    implementation(kotlin("test"))

    // Compose UI Testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0")

// Para inspeccionar el estado y elementos de Compose durante las pruebas
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")
}

kapt {
    correctErrorTypes = true
}
