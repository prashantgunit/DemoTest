import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "sample.demogithubaction"
    compileSdk = 34

    defaultConfig {
        applicationId = "sample.demogithubaction"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val readKeystorePropertiesFile = readProperties(keystorePropertiesFile)

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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs{
        create("devConfig") {
            storeFile =
                file("${rootDir}/keystores/" + readKeystorePropertiesFile["storeFile"])
            storePassword = readKeystorePropertiesFile["storePassword"].toString()
            keyAlias = readKeystorePropertiesFile["keyAlias"].toString()
            keyPassword = readKeystorePropertiesFile["keyPassword"].toString()
        }

        create("prodConfig") {
            storeFile =
                file("${rootDir}/keystores/" + readKeystorePropertiesFile["storeFile"])
            storePassword = readKeystorePropertiesFile["storePassword"].toString()
            keyAlias = readKeystorePropertiesFile["keyAlias"].toString()
            keyPassword = readKeystorePropertiesFile["keyPassword"].toString()
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        create("Dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
//            signingConfig = signingConfigs["devConfig"]
        }

        create("Prod"){
            dimension = "environment"
            applicationIdSuffix = ".prod"
//            signingConfig = signingConfigs["prodConfig"]
        }
        /*prod {
            dimension "environment"
            // No applicationIdSuffix or versionNameSuffix for the production flavor
        }*/
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

fun readProperties(propertiesFile: File) = Properties().apply {
    propertiesFile.inputStream().use { fis ->
        load(fis)
    }
}