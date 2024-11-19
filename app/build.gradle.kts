plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.dreadful"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dreadful"
        minSdk = 29
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("com.android.tools:desugar_jdk_libs:2.1.3")
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.rendering)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}