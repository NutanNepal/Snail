plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.lumu.snail"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.lumu.snail"
        minSdk = 30
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}


dependencies {
    // https://mvnrepository.com/artifact/io.github.amrdeveloper/codeview
    implementation("io.github.amrdeveloper:codeview:1.3.8")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("androidx.webkit:webkit:1.7.0")
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation ("androidx.recyclerview:recyclerview:1.3.0")
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation("androidx.preference:preference:1.2.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    androidTestImplementation("junit:junit:4.12")
}