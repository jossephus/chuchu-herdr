plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

// herdr for chuchu: native splits for herdr (https://github.com/herdrdev/herdr) sessions,
// ported from chuchu PR #69. Ships as its own plugin APK that chuchu loads once enabled.
android {
    namespace = "com.jossephus.chuchu.plugins.herdr"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.jossephus.chuchu.herdr"
        // java.util.Base64 for herdr's frame payloads.
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Provided by chuchu at runtime, so compileOnly: chuchu's class loader answers first for
    // these, and bundled copies would only be dead weight (or newer than chuchu's).
    compileOnly(libs.chuchu.plugin.api)
    compileOnly(platform(libs.androidx.compose.bom))
    compileOnly(libs.androidx.compose.runtime)
    compileOnly(libs.androidx.compose.ui)
    compileOnly(libs.androidx.compose.foundation)
    // Bundled: chuchu doesn't share it, so the plugin's own copy is used.
    implementation(libs.kotlinx.serialization.json)

    // compileOnly deps aren't on the unit-test classpath; tests run without chuchu.
    testImplementation(libs.chuchu.plugin.api)
    testImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.androidx.compose.runtime)
    testImplementation(libs.androidx.compose.ui)
    testImplementation(libs.junit)
}

// kotlin-stdlib comes from chuchu at runtime; keep it out of the APK but not out of tests.
configurations.configureEach {
    if (name == "debugRuntimeClasspath" || name == "releaseRuntimeClasspath") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains", module = "annotations")
    }
}
