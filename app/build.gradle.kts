plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdkVersion(Config.compileSdk)
    androidExtensions {
        isExperimental = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        applicationId = "io.github.steelahhh"
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
        versionCode = Config.appVersionCode
        versionName = Config.appVersionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument("runnerBuilder", "de.mannodermaus.junit5.AndroidJUnit5Builder")
    }

    packagingOptions {
        exclude("META-INF/LICENSE*")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = true
            isUseProguard = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(Dependencies.kotlin)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.constraint)
    implementation(Dependencies.material)

    implementation(Dependencies.Conductor.core)
    implementation(Dependencies.Conductor.lifecycle)

    implementation(Dependencies.FastAdapter.ui)
    implementation(Dependencies.FastAdapter.diff)
    implementation(Dependencies.FastAdapter.utils)
    implementation(Dependencies.FastAdapter.scroll)

    implementation(Dependencies.timber)

    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.viewModel)
    implementation(Dependencies.Koin.scope)

    implementation(Dependencies.Room.core)
    implementation(Dependencies.Room.rx)
    kapt(Dependencies.Room.compiler)

    implementation(Dependencies.Mobius.core)
    implementation(Dependencies.Mobius.rx)
    implementation(Dependencies.Mobius.extras)
    implementation(Dependencies.Mobius.android)

    implementation(Dependencies.Rx.kotlin)
    implementation(Dependencies.Rx.android)
    implementation(Dependencies.Rx.relay)

    implementation(Dependencies.RxBinding.core)
    implementation(Dependencies.RxBinding.appCompat)
    implementation(Dependencies.RxBinding.material)

    implementation(Dependencies.okHttpLoggingInterceptor)
    implementation(Dependencies.Retrofit.core)
    implementation(Dependencies.Retrofit.rx)
    implementation(Dependencies.Retrofit.gson)
    implementation(Dependencies.gson)

    debugImplementation(Dependencies.leakCanary)
    implementation(Dependencies.leakSentry)

    testImplementation(Dependencies.Room.test)

    testImplementation(Dependencies.Koin.test)
    testImplementation(Dependencies.Mobius.test)

    androidTestImplementation(Dependencies.testRunner)

    testImplementation(Dependencies.Mockk.core)
    testImplementation(Dependencies.JUnit.api)
    testRuntimeOnly(Dependencies.JUnit.engine)

    androidTestImplementation(Dependencies.JUnit.api)
    androidTestImplementation(Dependencies.Mockk.android)
    androidTestImplementation(Dependencies.JUnit.Android.core)
    androidTestRuntimeOnly(Dependencies.JUnit.Android.runner)
    androidTestRuntimeOnly(Dependencies.JUnit.engine)
}
