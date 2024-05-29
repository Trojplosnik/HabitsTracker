import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties


plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")


        val key: String = gradleLocalProperties(rootDir, providers).getProperty("API_TOKEN") ?: ""
        buildConfigField("String", "API_TOKEN", "\"${key}\"")
    }

    buildFeatures {
        buildConfig = true
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
}

dependencies {
    //Modules
    implementation(project(":domain"))

    //Room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)


    //Retrofit
    implementation(libs.retrofit)

    //Gson
    implementation (libs.converter.gson)

    //Okhttp
    implementation (libs.okhttp)
    implementation (libs.squareup.logging.interceptor)

    //hilt
    implementation (libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.fragment.ktx)


    //test
    testImplementation(libs.mockito.core)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.junit)
}