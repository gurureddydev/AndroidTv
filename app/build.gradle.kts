plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.guru.androidtv"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.guru.androidtv"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    val leanback_version = "1.2.0-alpha04"

    implementation ("androidx.leanback:leanback:1.0.0")
    // leanback-preference is an add-on that provides a settings UI for TV apps.
    implementation("androidx.leanback:leanback-preference:$leanback_version")
    // leanback-paging is an add-on that simplifies adding paging support to a RecyclerView Adapter.
    implementation("androidx.leanback:leanback-paging:1.1.0-alpha11")
    // leanback-tab is an add-on that provides customized TabLayout to be used as the top navigation bar.
    implementation("androidx.leanback:leanback-tab:1.1.0-beta01")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation ("com.google.android.exoplayer:exoplayer-core:2.17.1")
    implementation ("com.google.android.exoplayer:exoplayer-ui:2.17.1")
    implementation ("com.google.android.exoplayer:exoplayer-dash:2.17.1")

    // Exoplayer for playback
    val exoplayer_version = "2.17.1"
    implementation ("com.google.android.exoplayer:exoplayer-core:$exoplayer_version")
    implementation ("com.google.android.exoplayer:extension-leanback:$exoplayer_version")
    implementation ("com.google.android.exoplayer:extension-mediasession:$exoplayer_version")

    /* livedata  view-model */
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

}