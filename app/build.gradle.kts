plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
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
        multiDexEnabled = true
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
    implementation(project(":core_app"))
//    implementation(project(":core_app"))
//    implementation("com.android.volley:volley:1.2.1")
    implementation(Dependencies.volley)
//    implementation("com.google.firebase:firebase-crashlytics-ktx:18.6.2")
    implementation(Dependencies.firebaseCrashlytics)
//    val leanback_version = "1.2.0-alpha04"

//    implementation("androidx.leanback:leanback:1.0.0")
    implementation(Dependencies.leanback)
    // leanback-preference is an add-on that provides a settings UI for TV apps.
//    implementation("androidx.leanback:leanback-preference:$leanback_version")
    implementation(Dependencies.leanbackPreference)
    // leanback-paging is an add-on that simplifies adding paging support to a RecyclerView Adapter.
//    implementation("androidx.leanback:leanback-paging:1.1.0-alpha11")
    implementation(Dependencies.leanbackPaging)
    // leanback-tab is an add-on that provides customized TabLayout to be used as the top navigation bar.
//    implementation("androidx.leanback:leanback-tab:1.1.0-beta01")
    implementation(Dependencies.leanbackTab)
//    implementation("androidx.core:core-ktx:1.12.0")
    implementation(Dependencies.coreKtx)
//    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation(Dependencies.appcompat)
//    implementation("com.google.android.material:material:1.11.0")
    implementation(Dependencies.material)
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(Dependencies.constraintLayout)
//    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation(Dependencies.glide)
//    implementation("com.google.code.gson:gson:2.8.9")
    implementation(Dependencies.gson)
//    implementation("com.google.android.exoplayer:exoplayer-core:2.17.1")
    implementation(Dependencies.exoplayerCore)
//    implementation("com.google.android.exoplayer:exoplayer-ui:2.17.1")
    implementation(Dependencies.exoplayerUi)
//    implementation("com.google.android.exoplayer:exoplayer-dash:2.17.1")
    implementation(Dependencies.exoplayerDash)

    // Exoplayer for playback
//    val exoplayer_version = "2.17.1"
//    implementation("com.google.android.exoplayer:extension-leanback:$exoplayer_version")
    implementation(Dependencies.exoplayerLeanback)
//    implementation("com.google.android.exoplayer:extension-mediasession:$exoplayer_version")
    implementation(Dependencies.exoplayerMediaSession)

    /* livedata  view-model */
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation(Dependencies.lifecycleLiveDataKtx)
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation(Dependencies.lifecycleViewModelKtx)

//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation(Dependencies.retrofit)
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(Dependencies.retrofitConverterGson)
//
//    val multidex_version = "2.0.1"
//    implementation("androidx.multidex:multidex:$multidex_version")
    implementation(Dependencies.multidex)
//    implementation ("com.github.HaarigerHarald:android-youtubeExtractor:2.1.0")
    implementation(Dependencies.youtubeExtractor)

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation ("com.google.firebase:firebase-analytics:21.5.1")


    // Add the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
}