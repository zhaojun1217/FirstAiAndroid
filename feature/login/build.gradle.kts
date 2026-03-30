import org.jetbrains.kotlin.gradle.dsl.JvmTarget
val isLoginAlone = false

plugins {
    alias(libs.plugins.android.library)
//    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.zhaojun.feature.login"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

//        if (isLoginAlone) {
//            applicationId = "com.zhaojun.feature.login"
//            versionCode = 1
//            versionName = "1.0"
//            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        }
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

    buildFeatures {
        compose = true
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
}

dependencies {
    implementation(project(":common:core"))
    implementation(project(":common:router"))
    implementation(project(":common:ui"))
    implementation(project(":common:network"))

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    kapt(libs.therouter.apt)
    ksp(libs.moshi.kotlin.codegen)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    if (isLoginAlone) {
        implementation(libs.androidx.appcompat)
    }
}