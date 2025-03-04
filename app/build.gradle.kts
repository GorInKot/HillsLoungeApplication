import org.jetbrains.kotlin.psi.stubs.impl.serialize

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    //id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.hillsloungeapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hillsloungeapplication"
        minSdk = 31
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

    buildFeatures {
        viewBinding = true
        dataBinding = true

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation("com.google.firebase:firebase-messaging-ktx:24.1.0")

    implementation("com.google.android.material:material:1.12.0")
    //splashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // для закругления углов изображения
    implementation("jp.wasabeef:glide-transformations:4.3.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.firestore.ktx)

    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1") // Для генерации кода Glide

    implementation("com.yandex.android:maps.mobile:4.10.1-full")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")

    // room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.database.ktx)

    annotationProcessor(libs.room.runtime)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation (libs.androidx.fragment.ktx)

    implementation(libs.material)

    implementation (libs.glide)

    implementation(libs.retrofit)
    implementation (libs.retrofit2.converter.gson)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.volley)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}