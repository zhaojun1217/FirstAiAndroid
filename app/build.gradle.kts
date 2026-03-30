import org.jetbrains.kotlin.gradle.dsl.JvmTarget
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    id("therouter")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

val loginAlone = project.findProperty("loginAlone")?.toString()?.toBoolean() ?: false
val homeAlone = project.findProperty("homeAlone")?.toString()?.toBoolean() ?: false
val mineAlone = project.findProperty("mineAlone")?.toString()?.toBoolean() ?: false

android {
    namespace = "com.zhaojun.firstaiandroid"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.zhaojun.firstaiandroid"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget("17")
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":common:core"))
    implementation(project(":common:router"))
    implementation(project(":common:ui"))
    implementation(project(":common:network"))
    if (!loginAlone) {
        implementation(project(":feature:login"))
    }
    if (!homeAlone) {
        implementation(project(":feature:home"))
    }
    if (!mineAlone) {
        implementation(project(":feature:mine"))
    }
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}