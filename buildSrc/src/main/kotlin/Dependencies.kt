object Dependencies {
    // Import versions
    private val versions = Versions

    // Define dependencies here
    const val volley = "com.android.volley:volley:${versions.volleyVersion}"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx:${versions.firebaseCrashlyticsVersion}"
    const val leanback = "androidx.leanback:leanback:1.0.0"
    const val leanbackPreference = "androidx.leanback:leanback-preference:${versions.leanbackVersion}"
    const val leanbackPaging = "androidx.leanback:leanback-paging:1.1.0-alpha11"
    const val leanbackTab = "androidx.leanback:leanback-tab:1.1.0-beta01"
    const val coreKtx = "androidx.core:core-ktx:${versions.coreKtxVersion}"
    const val appcompat = "androidx.appcompat:appcompat:${versions.appcompatVersion}"
    const val material = "com.google.android.material:material:${versions.materialVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${versions.constraintLayoutVersion}"
    const val glide = "com.github.bumptech.glide:glide:${versions.glideVersion}"
    const val gson = "com.google.code.gson:gson:${versions.gsonVersion}"
    const val exoplayerCore = "com.google.android.exoplayer:exoplayer-core:${versions.exoplayerVersion}"
    const val exoplayerUi = "com.google.android.exoplayer:exoplayer-ui:${versions.exoplayerVersion}"
    const val exoplayerDash = "com.google.android.exoplayer:exoplayer-dash:${versions.exoplayerVersion}"
    const val exoplayerLeanback = "com.google.android.exoplayer:extension-leanback:${versions.exoplayerVersion}"
    const val exoplayerMediaSession = "com.google.android.exoplayer:extension-mediasession:${versions.exoplayerVersion}"
    const val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${versions.lifecycleVersion}"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${versions.lifecycleVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${versions.retrofitVersion}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${versions.retrofitVersion}"
    const val multidex = "androidx.multidex:multidex:${versions.multidexVersion}"
    const val youtubeExtractor = "com.github.HaarigerHarald:android-youtubeExtractor:${versions.youtubeExtractorVersion}"
    const val firebaseBom = "com.google.firebase:firebase-bom:${versions.firebaseBomVersion}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"

}
